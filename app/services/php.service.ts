import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class PhpService {
    //protected static _host: string = "http://chat.metropolitan.ac.rs";
    protected static _host: string = "http://localhost";

    constructor(protected http: Http) {
    }

    protected static createOptions(content_headers = true) {
        let headers = new Headers();
        if(content_headers) {
            headers.append('Content-Type', 'application/x-www-form-urlencoded');
        }
        return new RequestOptions({headers: headers, withCredentials: true});
    }

}
