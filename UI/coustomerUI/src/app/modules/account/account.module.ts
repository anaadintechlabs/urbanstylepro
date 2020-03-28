import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule,ReactiveFormsModule } from '@angular/forms'
import { AccountRoutingModule } from './account.routing';


@NgModule({
  declarations: [
    LoginComponent,
    SignupComponent,
    ForgetPasswordComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    ReactiveFormsModule,
    AccountRoutingModule,
    RouterModule
  ],
  exports : [

  ]
})
export class AccountModule { }
