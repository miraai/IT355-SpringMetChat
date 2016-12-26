import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'
import {PhpService} from "./php.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class MessageService extends PhpService {

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
            PhpService._host + '/message' + args,
            options
        ).map((res: Response) => res.json());
    }

    getUserMessages(username: string, date: number = 0) {
        if(!username) {
            throw new Error('Parameter "username" missing.');
        }
        let args = '?username=' + encodeURIComponent(username);
        if(date) {
            args += '&newerThan=' + date;
        }

        let options = PhpService.createOptions(false);
        return this.http.get(
            PhpService._host + '/messages' + args,
            options
        ).map((res: Response) => res.json());
    }

    getGroupMessages(groupname: string, date: number = 0) {
        if(!groupname) {
            throw new Error('Parameter "groupname" missing.');
        }
        let args = '?groupname=' + encodeURIComponent(groupname);
        if(date) {
            args += '&newerThan=' + date;
        }

        let options = PhpService.createOptions(false);
        return this.http.get(
            PhpService._host + '/messages' + args,
            options
        ).map((res: Response) => res.json());
    }

    newMessage(user: string, kind: string, text: string) {
        let args = '';
        if(!kind) {
            throw new Error('Parameter "kind" missing.');
        }
        if(user) {
            args += '&' + kind + 'name=' + encodeURIComponent(user);
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
            PhpService._host + '/message/new',
            body,
            options
        ).map((res: Response) => res.json());
    }
}