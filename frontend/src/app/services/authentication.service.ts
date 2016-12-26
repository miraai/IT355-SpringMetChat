import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import {CookieService} from 'angular2-cookie/core';
import 'rxjs/add/operator/map'
import {PhpService} from "./php.service";
import {BehaviorSubject} from "rxjs";
import {User} from "../models/user";
import { Observable } from "rxjs/Observable";

@Injectable()
export class AuthenticationService extends PhpService {

    private _userSource;
    user$;

    constructor(http: Http,
                private cookieService: CookieService) {
        super(http);
        let user = JSON.parse(localStorage.getItem('user'));
        this._userSource = new BehaviorSubject<User>(user);
        this.user$ = this._userSource.asObservable();
    }

    private handleResponse(res: Response) {
        let user = res.json();
        if(!user) {
            console.log(res);
            return;
        }
        let token = this.cookieService.get('token');
        if(!token) {
            console.log('No token');
            return;
        }
        localStorage.setItem('user', JSON.stringify(user));
        this._userSource.next(user);
    }

    login(username, password) {
        let body =
            'username=' + encodeURIComponent(username) +
            '&password=' + encodeURIComponent(password);
        let options = PhpService.createOptions();
        return this.http.post(
            PhpService._host + '/login',
            body,
            options
        ).map((res: Response) => {
            this.handleResponse(res);
        });
    }

    register(username, password, avatar) {
        let body =
            'username=' + encodeURIComponent(username) +
            '&password=' + encodeURIComponent(password) +
            '&avatar=' + encodeURIComponent(avatar)
        let options = PhpService.createOptions();
        return this.http.post(
            PhpService._host + '/register',
            body,
            options
        ).map((res: Response) => {
            this.handleResponse(res);
        });
    }

    logout() {
        this.cookieService.remove('token');
        localStorage.removeItem('user');
        this._userSource.next(null);

        let options = PhpService.createOptions();
        return this.http.delete(
            PhpService._host + '/logout',
            options
        ).map((res: Response) => {
            this.cookieService.remove('token');
            localStorage.removeItem('user');
            this._userSource.next(null);
            return res.json();
        });
    }
}