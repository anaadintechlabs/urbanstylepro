import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressComponent } from './address/address.component';
import { BankDetailsComponent } from './bank-details/bank-details.component';
import { SignupComponent } from '../account/signup/signup.component'
import { LoginComponent } from '../account/login/login.component';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { AccountRoutes } from './account.routing';
import { RouterModule } from '@angular/router';
import { FormModule } from '../_forms/form.module';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';



@NgModule({
  declarations: [
    LoginComponent,
    SignupComponent,
    BankDetailsComponent,
    AddressComponent,
    FooterComponent,
    HeaderComponent,
    EditProfileComponent,
    ForgetPasswordComponent
  ],
  imports: [
    RouterModule.forChild(AccountRoutes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormModule,
    ToastrModule.forRoot() // ToastrModule added
  ]
})
export class AccountModule { }
