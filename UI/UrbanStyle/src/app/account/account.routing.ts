import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "../account/login/login.component";
import { SignupComponent } from "../account/signup/signup.component";
import { UserGuardGuard } from "src/_services/http_&_login/user-guard.guard";
import { AddressComponent } from "src/app/account/address/address.component";
import { AddAddressComponent } from "src/app/_forms/add-address/add-address.component";
import { NgModule } from "@angular/core";
import { BankDetailsComponent } from "src/app/account/bank-details/bank-details.component";
import { AddBankDetailsComponent } from "src/app/_forms/add-bank-details/add-bank-details.component";

export const AccountRoutes: Routes = [
  {
    path: "",
    redirectTo: "login",
    pathMatch: "full"
  },
  // account component needs to be changed
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "signup",
    component: SignupComponent
  },
  {
    path: "addresses",
    canActivate: [UserGuardGuard],
    component: AddressComponent
  },
  {
    path: "addresses/:action",
    canActivate: [UserGuardGuard],
    component: AddAddressComponent
  },

  {
    path: "addresses/:action/:id",
    canActivate: [UserGuardGuard],
    component: AddAddressComponent
  },
  {
    path: "bank_details",
    canActivate: [UserGuardGuard],
    component: BankDetailsComponent
  },
  {
    path: "bank_details/:action",
    canActivate: [UserGuardGuard],
    component: AddBankDetailsComponent
  },

  {
    path: "bank_details/:action/:id",
    canActivate: [UserGuardGuard],
    component: AddBankDetailsComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(AccountRoutes)],
  exports: [RouterModule]
})
export class VendorRoutingModule {}
