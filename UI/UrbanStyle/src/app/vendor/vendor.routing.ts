import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from './login/login.component';
import { VendorComponent } from './vendor.component';
import { SignupComponent } from './signup/signup.component';
import { AddProductComponent } from './add-product/add-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserGuardGuard } from 'src/_services/http_&_login/user-guard.guard';
import { AddressComponent } from "src/app/vendor/address/address.component";
import { AddAddressComponent } from "src/app/_forms/add-address/add-address.component";
import { NgModule } from "@angular/core";
import { BankDetailsComponent } from "src/app/vendor/bank-details/bank-details.component";
import { AddBankDetailsComponent } from "src/app/_forms/add-bank-details/add-bank-details.component";
import { InventoryComponent } from "src/app/vendor/inventory/inventory.component";

export const VendorRoutes: Routes=[
    {
        path:'',
        component : VendorComponent,
        children : [
            {
                path : '',
                component : DashboardComponent
            },
            {
                path : 'login',
                component : LoginComponent
            },
            {
                path : 'signup',
                component : SignupComponent
            },
              {
                path : 'inventory',
                component : InventoryComponent
            },
            // account component needs to be changed
             {
                path : 'account/addresses',
                 canActivate : [UserGuardGuard],
                component : AddressComponent,
            },
             {
                path : 'account/addresses/:action',
                 canActivate : [UserGuardGuard],
                component : AddAddressComponent,
            },

             {
                path : 'account/addresses/:action/:id',
                 canActivate : [UserGuardGuard],
                component : AddAddressComponent,
            },
             {
                path : 'account/bank_details',
                 canActivate : [UserGuardGuard],
                component : BankDetailsComponent,
            },
             {
                path : 'account/bank_details/:action',
                 canActivate : [UserGuardGuard],
                component : AddBankDetailsComponent,
            },

             {
                path : 'account/bank_details/:action/:id',
                 canActivate : [UserGuardGuard],
                component : AddBankDetailsComponent,
            },

            {
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
@NgModule({
  imports: [RouterModule.forRoot(VendorRoutes)],
  exports: [RouterModule]
})
export class VendorRoutingModule { }