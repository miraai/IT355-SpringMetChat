﻿import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

import { AlertService } from '../services/alert.service';
import {AuthenticationService} from "../services/authentication.service";

@Component({
    moduleId: module.id,
    selector: 'login',
    templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
    model: any = {};
    onPage: boolean;
    loading: boolean;

    constructor(
        private router: Router,
        private loginService: AuthenticationService,
        private alertService: AlertService) { }

    ngOnInit() {
        this.onPage = this.router.url === '/login';
    }

    login() {
        this.loading = true;
        this.alertService.clearMessage();
        this.loginService.login(this.model.username, this.model.password)
            .subscribe(
                data => {
                    console.log(data);
                    this.alertService.success('Login successful!', true);
                    this.router.navigate(['/']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
