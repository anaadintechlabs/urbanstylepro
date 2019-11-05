import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { VendorRoutes } from './vendor.routing';
import { LoginComponent } from './login/login.component';
import { CommonModule } from '@angular/common';

@NgModule({
    imports:[
        RouterModule.forChild(VendorRoutes),
        ReactiveFormsModule,
        FormsModule,
        CommonModule 
    ],
    declarations:[LoginComponent],
})

export class VendorModule{

}