import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { SignupComponent } from './signup/signup.component';
import { AuthGuardService } from 'src/_service/http_&_login/authGuard.service';


const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'forgotPass',
        component: ForgetPasswordComponent
    },
    {
        path: 'signup',
        component: SignupComponent,
    },
    {
        path: 'dashboard',
        canActivateChild : [AuthGuardService],
        loadChildren : () => import('../account/user-dashboard/user-dashboard.module').then( m => m.UserDashboardModule )
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccountRoutingModule { }
