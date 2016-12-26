import {Component, OnInit, OnDestroy} from '@angular/core';

import { User } from '../models/user';
import {AuthenticationService} from "../services/authentication.service";
import {Subscription, Observable} from "rxjs";
import {UserService} from "../services/user.service";
import {GroupService} from "../services/group.service";
import {MessageService} from "../services/message.service";
import {AlertService} from "../services/alert.service";
import {Message} from "../models/message";

@Component({
    templateUrl: './messenger.component.html',
    styleUrls: ['./messenger.component.css']
})

export class MessengerComponent implements OnInit, OnDestroy {
    currentUser: User;
    subscription: Subscription;

    timerSubscription: Subscription;

    userType: string = 'user';

    selectedUser: any = new Object();

    users: any[];
    groups: any[];
    messages: any[];

    searchText: string;
    messageText: string;

    messageSending: boolean;

    constructor(private alertService: AlertService,
                private authService: AuthenticationService,
                private userService: UserService,
                private groupService: GroupService,
                private messageService: MessageService) {
    }

    ngOnInit() {
        this.subscription = this.authService.user$.subscribe(user => this.currentUser = user);

        this.refreshUsers();
        this.refreshGroups();
        this.refreshMessages();

        this.alertService.clearMessage();

        let timer = Observable.timer(0, 1000);
        this.timerSubscription = timer.subscribe(() => {
            this.getNewMessages();
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.timerSubscription.unsubscribe();
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
        this.messageSending = true;
        let message = new Message();
        message.text = this.messageText;
        message.sender = this.currentUser.username;
        message.receiver = this.selectedUser.username;
        let kind = "user";
        if(!message.receiver) {
            message.receiver = this.selectedUser.name;
            kind = "group";
        }
        this.messages.push(message);
        this.messageText = "";
        this.messageService.newMessage(message.receiver, kind, message.text)
            .subscribe(
                data => {
                    message.id = data.id;
                    message.sender = data.sender;
                    message.receiver = data.receiver;
                    message.date = data.date;
                    message.text = data.text;
                    this.messageSending = false;
                },
                error => {
                    console.log(error);
                    this.alertService.error(error);
                    this.messageSending = false;
                });
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
        this.messages = [];
        if(!this.selectedUser || (!this.selectedUser.username && !this.selectedUser.name)) {
            return;
        }
        (this.selectedUser.username ?
            this.messageService.getUserMessages(this.selectedUser.username) :
            this.messageService.getGroupMessages(this.selectedUser.name))
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

    getNewMessages() {
        let date = 0;
        if(this.messages.length > 0) {
            date = this.messages[this.messages.length-1].date;
            if(date == 0) {
                return;
            }
        }
        let method;
        if(this.selectedUser.username) {
            method = this.messageService.getUserMessages(this.selectedUser.username, date);
        }
        else if(this.selectedUser.name) {
            method = this.messageService.getGroupMessages(this.selectedUser.name, date);
        }
        else {
            return;
        }

        method.subscribe(
            data => {
                console.log(data);
                this.messages.push(...data);
            },
            error => {
                console.log(error);
                this.alertService.error(error);
            });
    }
}