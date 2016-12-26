import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService } from '../services/alert.service';
import {AuthenticationService} from "../services/authentication.service";

@Component({
    selector: 'register',
    templateUrl: './register.component.html',
    styleUrls: ['./login.component.css']
})

export class RegisterComponent {
    model: any = { avatar: 1, prevAvatar: 36, nextAvatar: 2 };
    loading: boolean;

    constructor(
        private router: Router,
        private registerService: AuthenticationService,
        private alertService: AlertService) { }

    register() {
        this.loading = true;
        this.alertService.clearMessage();
        this.registerService.register(this.model.username, this.model.password, this.model.avatar)
            .subscribe(
                data => {
                    console.log(data);
                    this.alertService.success('Registration successful!', true);
                    this.router.navigate(['/']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    changeAvatar(i: number) {
        if (i > 0) {
            this.model.prevAvatar = this.model.avatar;
            this.model.avatar = this.model.nextAvatar;
            if(this.model.nextAvatar >= 36) {
                this.model.nextAvatar = 1;
            }
            else {
                this.model.nextAvatar = this.model.nextAvatar + 1;
            }
        }
        else if (i < 0) {
            this.model.nextAvatar = this.model.avatar;
            this.model.avatar = this.model.prevAvatar;
            if(this.model.prevAvatar <= 1) {
                this.model.prevAvatar = 36;
            }
            else {
                this.model.prevAvatar = this.model.prevAvatar - 1;
            }
        }
    }
}
