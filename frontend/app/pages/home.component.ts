import {Component, OnInit} from '@angular/core';

import { User } from '../models/user';
import {AuthenticationService} from "../services/authentication.service";
import {Subscription} from "rxjs";

@Component({
    moduleId: module.id,
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
    currentUser: User;
    subscription: Subscription;

    constructor(private authService: AuthenticationService) {

    }

    ngOnInit() {
        this.subscription = this.authService.user$.subscribe(user => this.currentUser = user);
    }
}