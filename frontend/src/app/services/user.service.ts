import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'
import {PhpService} from "./php.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class UserService extends PhpService {
    private _url: string = PhpService._host + '/users';

    constructor(http: Http) {
        super(http);
    }

    getUser(username: string) {
        if(!username) {
            throw new Error('Parameter "username" missing.');
        }
        let args = '?username=' + encodeURIComponent(username);

        let options = PhpService.createOptions(false);
        return this.http.get(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }

    getUsers(username: string = "") {
        let args = '';
        if(username) {
            args = '?username=' + encodeURIComponent(username);
        }
        let options = PhpService.createOptions(false);
        return this.http.get(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }

    createUser(username, email, password, type) {
        let args = '';
        if(username) {
            args += '&username=' + encodeURIComponent(username);
        }
        else {
            throw new Error('Parameter "username" missing.');
        }
        if(email) {
            args += '&email=' + encodeURIComponent(email);
        }
        else {
            throw new Error('Parameter "email" missing.');
        }
        if(password) {
            args += '&password=' + encodeURIComponent(password);
        }
        else {
            throw new Error('Parameter "password" missing.');
        }
        if(type) {
            args += '&type=' + encodeURIComponent(type);
        }
        else {
            throw new Error('Parameter "type" missing.');
        }

        let body = args.substr(1);
        let options = PhpService.createOptions();
        return this.http.post(
            this._url,
            body,
            options
        ).map((res: Response) => res.json());
    }

    updateUser(id, username = null, email = null, password = null, type = null) {
        let args = '';
        if(!id) {
            throw new Error('User ID missing.');
        }
        if(username) {
            args += '&username=' + encodeURIComponent(username);
        }
        if(email) {
            args += '&email=' + encodeURIComponent(email);
        }
        if(password) {
            args += '&password=' + encodeURIComponent(password);
        }
        if(type) {
            args += '&type=' + encodeURIComponent(type);
        }
        if(!args) {
            throw new Error('Parameters missing.');
        }
        args += '&id=' + encodeURIComponent(id);

        let body = args.substr(1);
        let options = PhpService.createOptions();
        return this.http.put(
            this._url,
            body,
            options
        ).map((res: Response) => res.json());
    }

    deleteUser(id) {
        if(!id) {
            throw new Error('User ID missing.');
        }
        let args = '?id=' + encodeURIComponent(id);

        let options = PhpService.createOptions(false);
        return this.http.delete(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }
}