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
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AddressComponent } from './address/address.component';
import { BankDetailsComponent } from './bank-details/bank-details.component';

import { InventoryComponent } from './inventory/inventory.component';


@NgModule({
    imports:[
        RouterModule.forChild(VendorRoutes),
        CommonModule,
        FormModule,
        ServicesModule,
        NgbModule,
        ServicesModule,RouterModule
    ],
    
    declarations:[
        LoginComponent,
        VendorComponent,
        HeaderComponent,
        SidebarComponent,
        SignupComponent,
        AddProductComponent,
        DashboardComponent,
        AddressComponent,
        BankDetailsComponent,
        InventoryComponent,

        
    ],
    bootstrap: [VendorComponent]
})

export class VendorModule{}