import {Component, OnInit, ViewChild, SimpleChange} from '@angular/core';

import { User } from '../models/user';
import { UserService } from '../services/user.service';
import {AlertService} from "../services/alert.service";
import {Subscription} from "rxjs";
import {AuthenticationService} from "../services/authentication.service";
import {DataTable} from "angular2-datatable";

@Component({
    templateUrl: './user_list.component.html'
})

export class UserListComponent implements OnInit {
    subscription: Subscription;
    loading: boolean;

    @ViewChild('mf') dataTable: DataTable;

    currentUser: User;

    users: User[] = [];

    constructor(
        private userService: UserService,
        private alertService: AlertService,
        private authService: AuthenticationService) {
        this.loading = true;
    }

    ngOnInit() {
        this.subscription = this.authService.user$.subscribe(user => this.currentUser = user);
        this.userService.getUsers().subscribe(
            data => {
                this.users = data;
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                console.error(error);
                this.loading = false;
            });
    }

    deleteUser(id) {
        this.alertService.clearMessage();
        let deletingUser = this.users.find(user => user.id == id);
        deletingUser.deleting = true;
        this.userService.deleteUser(id).subscribe(
            data => {
                let index = this.users.map(user => user.id).indexOf(id);
                if(index == -1) {
                    return;
                }
                this.users.splice(index, 1);
                this.dataTable.ngOnChanges({'inputData' : new SimpleChange(this.users, this.users) });
                this.alertService.success(data);
            },
            error => {
                deletingUser.deleting = false;
                this.alertService.error(error);
                console.error(error);
            });
    }
}