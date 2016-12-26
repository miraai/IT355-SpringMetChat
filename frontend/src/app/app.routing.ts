import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";

import { LoginComponent } from './pages/login.component';
import { RegisterComponent } from './pages/register.component';
import { AuthGuard } from './guards/auth.guard';
import {HomeComponent} from "./pages/home.component";
import {AdminGuard} from "./guards/admin.guard";
import {UserTableComponent} from "./pages/user_table.component";
import {MessengerComponent} from "./pages/messenger.component";

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'users', component: UserTableComponent, canActivate: [AdminGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'message', component: MessengerComponent, canActivate: [AuthGuard] },
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);