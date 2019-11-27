import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategotySelectionComponent } from './categoty-selection/categoty-selection.component';
import { MatCheckboxModule,MatSidenavModule,MatToolbarModule,MatIconModule, MatButtonModule, MatStepperModule, MatExpansionModule  } from '@angular/material';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { BasicDetailsComponent } from './basic-details/basic-details.component';
import { LoginComponent } from './login/login.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { VitalInformationComponent } from './vital-information/vital-information.component';
import { AddProductDesciprionComponent } from './add-product-desciprion/add-product-desciprion.component';
import { AddProductVariationComponent } from './add-product-variation/add-product-variation.component';
import { AddProductMediaComponent } from './add-product-media/add-product-media.component';



@NgModule({
  declarations: [CategotySelectionComponent, 
    BasicDetailsComponent, 
    LoginComponent, VitalInformationComponent, AddProductDesciprionComponent, AddProductVariationComponent, AddProductMediaComponent
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
    MatCheckboxModule,
    NgbModule,
    MatExpansionModule
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
    AddProductDesciprionComponent,
    AddProductVariationComponent,
    AddProductMediaComponent
  ]
})
export class FormModule { }
