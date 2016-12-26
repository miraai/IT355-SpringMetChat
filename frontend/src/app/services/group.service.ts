import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'
import {PhpService} from "./php.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class GroupService extends PhpService {
    private _url: string = PhpService._host + '/groups';

    constructor(http: Http) {
        super(http);
    }

    getGroup(name: string) {
        if(!name) {
            throw new Error('Parameter "name" missing.');
        }
        let args = '?name=' + encodeURIComponent(name);

        let options = PhpService.createOptions(false);
        return this.http.get(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }

    getGroups(name: string = "") {
        let args = '';
        if(name) {
            args = '?name=' + encodeURIComponent(name);
        }
        let options = PhpService.createOptions(false);
        return this.http.get(
            this._url + args,
            options
        ).map((res: Response) => res.json());
    }

    createGroup(name) {
        let args = '';
        if(name) {
            args += '&name=' + encodeURIComponent(name);
        }
        else {
            throw new Error('Parameter "name" missing.');
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