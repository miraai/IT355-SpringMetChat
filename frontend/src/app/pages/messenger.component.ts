import {Component, OnInit} from '@angular/core';

import { User } from '../models/user';
import {AuthenticationService} from "../services/authentication.service";
import {Subscription} from "rxjs";
import {UserService} from "../services/user.service";
import {GroupService} from "../services/group.service";
import {MessageService} from "../services/message.service";
import {AlertService} from "../services/alert.service";
import {Message} from "../models/message";

@Component({
    templateUrl: './messenger.component.html',
    styleUrls: ['./messenger.component.css']
})

export class MessengerComponent implements OnInit {
    currentUser: User;
    subscription: Subscription;

    selectedUser: User;

    users: any[];
    groups: any[];
    messages: any[];

    searchText: string;
    messageText: string;

    constructor(private alertService: AlertService,
                private authService: AuthenticationService,
                private userService: UserService,
                private groupService: GroupService,
                private messageService: MessageService) {
        this.selectedUser = new User();
    }

    ngOnInit() {
        this.subscription = this.authService.user$.subscribe(user => this.currentUser = user);
        this.refreshUsers();
        this.refreshGroups();
        this.refreshMessages();
        this.alertService.clearMessage();
    }

    selectUser(user) {
        this.selectedUser = user;
        this.refreshMessages();
    }

    search() {
        this.refreshUsers();
        this.refreshGroups()
    }

    sendMessage() {
        let message = new Message();
        message.text = this.messageText;
        message.sender = this.currentUser.username;
        message.date = 1234;
        this.messages.push(message);
        this.messageText = "";
    }

    refreshUsers() {
        this.userService.getUsers(this.searchText)
            .subscribe(
                data => {
                    console.log(data);
                    this.users = data;
                },
                error => {
                    console.log(error);
                    this.alertService.error(error);
                });
    }

    refreshGroups() {
        this.groupService.getGroups(this.searchText)
            .subscribe(
                data => {
                    console.log(data);
                    this.groups = data;
                },
                error => {
                    console.log(error);
                    this.alertService.error(error);
                });
    }

    refreshMessages() {
        if(!this.selectedUser || !this.selectedUser.username) {
            this.messages = [];
            return;
        }
        this.messageService.getMessages(this.selectedUser.username)
            .subscribe(
                data => {
                    console.log(data);
                    this.messages = data;
                },
                error => {
                    console.log(error);
                    this.alertService.error(error);
                });
    }
}