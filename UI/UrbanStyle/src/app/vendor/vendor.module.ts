import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { VendorRoutes } from './vendor.routing';
import { LoginComponent } from './login/login.component';
import { CommonModule } from '@angular/common';
import { VendorComponent } from './vendor.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SignupComponent } from './signup/signup.component'
import { FormModule } from '../_forms/form.module';
// import { ReactiveFormsModule,FormsModule } from '@angular/forms';

@NgModule({
    imports:[
        RouterModule.forChild(VendorRoutes),
        CommonModule,
        FormModule,
        // ReactiveFormsModule,
        // FormsModule
    ],
    
    declarations:[
        LoginComponent,
        VendorComponent,
        HeaderComponent,
        SidebarComponent,
        SignupComponent
    ],
})

export class VendorModule{}