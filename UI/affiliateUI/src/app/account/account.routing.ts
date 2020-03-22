import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "../account/login/login.component";
import { SignupComponent } from "../account/signup/signup.component";
import { AddressComponent } from "src/app/account/address/address.component";
import { NgModule } from "@angular/core";
import { BankDetailsComponent } from "src/app/account/bank-details/bank-details.component";
import { AuthGuardService } from 'src/_services/http_&_login/authGuard.service';

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
    canActivate: [AuthGuardService],
    component: AddressComponent
  },

  {
    path: "bank_details",
    canActivate: [AuthGuardService],
    component: BankDetailsComponent
  },

];
@NgModule({
  imports: [RouterModule.forRoot(AccountRoutes)],
  exports: [RouterModule]
})
export class VendorRoutingModule {}
