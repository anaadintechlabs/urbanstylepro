import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategotySelectionComponent } from './categoty-selection/categoty-selection.component';
import {
  MatCheckboxModule,
  MatSidenavModule,
  MatToolbarModule,
  MatIconModule,
  MatButtonModule,
  MatStepperModule,
  MatFormFieldModule,
  MatInputModule,
  MatRadioModule,
  MatSelectModule,
  MatCardModule
} from "@angular/material";
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { BasicDetailsComponent } from './basic-details/basic-details.component';
import { LoginComponent } from './login/login.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { VitalInformationComponent } from './vital-information/vital-information.component';
import { AddProductDesciprionComponent } from './add-product-desciprion/add-product-desciprion.component';
import { AddProductVariationComponent } from './add-product-variation/add-product-variation.component';
import { AddProductMediaComponent } from './add-product-media/add-product-media.component';
import { AddAddressComponent } from './add-address/add-address.component';
import { AddBankDetailsComponent } from './add-bank-details/add-bank-details.component';
import { AddCategoryComponent } from './add-category/add-category.component';



@NgModule({
  declarations: [CategotySelectionComponent, 
    BasicDetailsComponent, 
    LoginComponent, VitalInformationComponent, AddProductDesciprionComponent, AddProductVariationComponent, AddProductMediaComponent, AddBankDetailsComponent,AddAddressComponent, AddCategoryComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatStepperModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatCardModule,
     MatInputModule, MatFormFieldModule, MatButtonModule, MatRadioModule, MatSelectModule,
     MatIconModule,  MatCheckboxModule, MatToolbarModule,     
    ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatButtonModule,
    MatStepperModule,
    CategotySelectionComponent,
    BasicDetailsComponent,
    LoginComponent,
    MatFormFieldModule,
    MatInputModule,
    VitalInformationComponent,
    AddProductDesciprionComponent,
    AddProductVariationComponent,
    AddProductMediaComponent,
    AddAddressComponent,
    AddBankDetailsComponent,
    AddCategoryComponent
  ]
})
export class FormModule { }
