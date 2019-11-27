import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { VendorComponent } from './vendor.component';
import { SignupComponent } from './signup/signup.component';
import { AddProductComponent } from './add-product/add-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserGuardGuard } from 'src/_services/user-guard.guard';

export const VendorRoutes: Routes=[
    {
        path:'',
        component : VendorComponent,
        children : [
            {
                path : 'login',
                component : LoginComponent
            },{
                path : 'signup',
                component : SignupComponent
            },{
                path : 'dashboard',
                component : DashboardComponent,
                canActivate : [UserGuardGuard],
                children : [
                    {
                        path : 'addProduct',
                        component : AddProductComponent
                    },
                ]
            }
        ]
    }
];