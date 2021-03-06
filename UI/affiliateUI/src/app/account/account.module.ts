import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressComponent } from './address/address.component';
import { BankDetailsComponent } from './bank-details/bank-details.component';
import { SignupComponent } from '../account/signup/signup.component'
import { LoginComponent } from '../account/login/login.component';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { AccountRoutes } from './account.routing';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { FormModule } from '../_forms/form.module';



@NgModule({
  declarations: [
    LoginComponent,
    SignupComponent,
    BankDetailsComponent,
    AddressComponent,
  ],
  imports: [
    RouterModule.forChild(AccountRoutes),
    CommonModule,
    FormsModule,
    FormModule,
    ReactiveFormsModule,
    ToastrModule.forRoot() // ToastrModule added
  ]
})
export class AccountModule { }
