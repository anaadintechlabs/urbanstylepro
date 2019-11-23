import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategotySelectionComponent } from './categoty-selection/categoty-selection.component';
import { MatSidenavModule,MatToolbarModule,MatIconModule, MatButtonModule, MatStepperModule  } from '@angular/material';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { BasicDetailsComponent } from './basic-details/basic-details.component';
import { LoginComponent } from './login/login.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { VitalInformationComponent } from './vital-information/vital-information.component';
import { AddProductDesciprionComponent } from './add-product-desciprion/add-product-desciprion.component';
import { AddProductVariationComponent } from './add-product-variation/add-product-variation.component';



@NgModule({
  declarations: [CategotySelectionComponent, 
    BasicDetailsComponent, 
    LoginComponent, VitalInformationComponent, AddProductDesciprionComponent, AddProductVariationComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatStepperModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatStepperModule,
    CategotySelectionComponent,
    BasicDetailsComponent,
    LoginComponent,
    VitalInformationComponent,
    AddProductDesciprionComponent
  ]
})
export class FormModule { }
