import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService } from '../services/alert.service';
import {AuthenticationService} from "../services/authentication.service";

@Component({
    moduleId: module.id,
    selector: 'register',
    templateUrl: 'register.component.html',
    styleUrls: ['login.component.css']
})

export class RegisterComponent {
    model: any = {};
    onPage: boolean;
    loading: boolean;

    constructor(
        private router: Router,
        private registerService: AuthenticationService,
        private alertService: AlertService) { }

    ngOnInit() {
        this.onPage = this.router.url === '/register';
    }

    register() {
        this.loading = true;
        this.alertService.clearMessage();
        this.registerService.register(this.model.username, this.model.email, this.model.password)
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
}
