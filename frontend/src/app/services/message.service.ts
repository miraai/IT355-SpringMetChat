import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'
import {PhpService} from "./php.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class MessageService extends PhpService {
    private _url: string = PhpService._host + '/messages';

    constructor(http: Http) {
        super(http);
    }

    getMessage(id: string) {
        if(!id) {
            throw new Error('Parameter "id" missing.');
        }
        let args = '?id=' + encodeURIComponent(id);

        let options = PhpService.createOptions(false);
        return this.http.get(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }

    getMessages(username: string) {
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

    newMessage(user, text) {
        let args = '';
        if(user) {
            args += '&username=' + encodeURIComponent(user);
        }
        else {
            throw new Error('Parameter "name" missing.');
        }
        if(text) {
            args += '&text=' + encodeURIComponent(text);
        }
        else {
            throw new Error('Parameter "text" missing.');
        }

        let body = args.substr(1);
        let options = PhpService.createOptions();
        return this.http.post(
            this._url,
            body,
            options
        ).map((res: Response) => res.json());
    }
}