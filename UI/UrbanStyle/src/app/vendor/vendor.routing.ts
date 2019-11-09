import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { VendorComponent } from './vendor.component';
import { SignupComponent } from './signup/signup.component';

export const VendorRoutes: Routes=[
    {
        path:'',
        component : VendorComponent,
        children: [
            {
                path : 'login',
                component : LoginComponent
            },
            {
                path : 'signup',
                component : SignupComponent
            }
        ]
    }
];