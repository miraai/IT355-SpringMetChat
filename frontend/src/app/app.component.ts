import {Component, OnInit, OnDestroy} from '@angular/core';
import {User} from "./models/user";
import {AuthenticationService} from "./services/authentication.service";
import {Router} from "@angular/router";
import {AlertService} from "./services/alert.service";
import {Subscription} from "rxjs/Subscription";
import {Observable} from "rxjs";
import {OverlayService} from "./services/overlay.service";

@Component({
    selector: 'app',
    templateUrl: './app.component.html'
})

export class AppComponent implements OnInit, OnDestroy {
    quotes: string[] = [
        '"Challenges are what makes life interesting; overcoming them is what makes life meaningful."',
        '"It is not the mountain we conquer, but ourselves." - Sir Edmund Hillary',
        '"It\'s easier to go down a hill than up it but the view is much better at the top." - Henry Ward Beecher',
        '"Only those who will risk going too far can possibly find out how far one can go." - T. S. Eliot',
        '"You have brains in your head. You have feet in your shoes. You can steer yourself, any direction you choose." - Dr. Seuss',
        '"One foot in front of the other. Repeat as often as necessary to finish." - Haruki Marukami',
        '"The most important key to achieving great success is to decide upon your goal and launch, get started, take action, move." - John Wooden',
        '"Do a little more each day than you think you possibly can." - Lowell Thomas',
        '"The real purpose of running isn\'t to win a race; it\'s to test the limits of the human heart." - Bill Bowerman',
        '"Anybody running beats anybody walking, and anybody walking beats anybody sitting." - Tom Bunk',
        '"A run begins the moment you forget you are running." - Adidas',
        '"Runs end. Running doesn\'t." - Nike',
        '"Just do it!" - Nike',
        '"There is no \'Y\' in running. Believe in the run." - Nike'
    ];

    currentQuote: number = 0;
    timerSubscription: Subscription;

    subscription: Subscription;
    loading: boolean;

    currentUser: User;

    constructor(private authService: AuthenticationService,
                private alertService: AlertService,
                private overlayService: OverlayService,
                private router: Router){
    }

    ngOnInit() {
        this.subscription = this.authService.user$.subscribe(user => this.currentUser = user);
        let timer = Observable.timer(0, 30000);
        this.timerSubscription = timer.subscribe(() => {
            this.currentQuote = Math.floor(Math.random() * this.quotes.length);
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.timerSubscription.unsubscribe();
    }

    logout() {
        this.loading = true;
        this.alertService.clearMessage();
        this.overlayService.showMessage("Logging out...");
        this.authService.logout()
            .subscribe(
                data => {
                    this.alertService.success(data, true);
                    this.loading = false;
                    this.overlayService.clearMessage();
                    this.router.navigate(['/']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                    this.overlayService.clearMessage();
                }
            );
    }
}