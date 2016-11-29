import { Routes, RouterModule } from '@angular/router';

import { UserListComponent } from './pages/user_list.component';
import { LoginComponent } from './pages/login.component';
import { RegisterComponent } from './pages/register.component';
import { AuthGuard } from './guards/auth.guard';
import {HomeComponent} from "./pages/home.component";
import {AdminGuard} from "./guards/admin.guard";

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'users', component: UserListComponent, canActivate: [AdminGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);