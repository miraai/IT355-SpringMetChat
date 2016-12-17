import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import {HttpModule, JsonpModule} from '@angular/http';
import { AppComponent }  from './app.component';
import { routing }        from './app.routing';

import { AlertComponent } from './directives/alert.component';
import { AdminGuard } from './guards/admin.guard';
import { AuthGuard } from './guards/auth.guard';
import { AlertService } from './services/alert.service';
import { UserService } from './services/user.service';
import { UserListComponent } from './pages/user_list.component';
import { LoginComponent } from './pages/login.component';
import { RegisterComponent } from './pages/register.component';
import {HomeComponent} from "./pages/home.component";
import {AuthenticationService} from "./services/authentication.service";
import { CookieService } from 'angular2-cookie/services/cookies.service';
import {PhpService} from "./services/php.service";
import {EmailValidator} from "./directives/email.validator";
import {NospaceValidator} from "./directives/nospace.validator";
import {NumberValidator} from "./directives/number.validator";
import {NumberSorter} from "./directives/number.sorter";
import {UserTypePipe} from "./directives/user_type.pipe";
import {DataTableModule} from "angular2-datatable";
import {OverlayService} from "./services/overlay.service";
import {OverlayComponent} from "./directives/overlay.component";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        JsonpModule,
        DataTableModule,
        routing
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        OverlayComponent,
        UserListComponent,
        LoginComponent,
        RegisterComponent,
        HomeComponent,
        EmailValidator,
        NospaceValidator,
        NumberValidator,
        NumberSorter,
        UserTypePipe
    ],
    providers: [
        AdminGuard,
        AuthGuard,
        AlertService,
        OverlayService,
        CookieService,
        PhpService,
        AuthenticationService,
        UserService
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }