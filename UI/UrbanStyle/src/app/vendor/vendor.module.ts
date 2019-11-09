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
import { ServicesModule } from 'src/_services/services.module';
import { AddProductComponent } from './add-product/add-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
// import { ReactiveFormsModule,FormsModule } from '@angular/forms';

@NgModule({
    imports:[
        RouterModule.forChild(VendorRoutes),
        CommonModule,
        FormModule,
        ServicesModule
    ],
    
    declarations:[
        LoginComponent,
        VendorComponent,
        HeaderComponent,
        SidebarComponent,
        SignupComponent,
        AddProductComponent,
        DashboardComponent
    ],
    bootstrap: [VendorComponent]
})

export class VendorModule{}