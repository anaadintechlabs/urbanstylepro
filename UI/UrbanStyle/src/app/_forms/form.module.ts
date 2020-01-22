import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { CategotySelectionComponent } from "../vendor/categoty-selection/categoty-selection.component";

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
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { BasicDetailsComponent } from "../vendor/basic-details/basic-details.component";
import { LoginComponent } from "./login/login.component";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { AddAddressComponent } from "./add-address/add-address.component";
import { AddBankDetailsComponent } from "./add-bank-details/add-bank-details.component";
import { AddCategoryComponent } from "./add-category/add-category.component";
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    CategotySelectionComponent,
    BasicDetailsComponent,
    LoginComponent,
    AddCategoryComponent,
    AddBankDetailsComponent,
    AddAddressComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
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
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatRadioModule,
    MatSelectModule,
    MatIconModule,
    MatCheckboxModule,
    MatToolbarModule
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
    AddAddressComponent,
    AddBankDetailsComponent,
    AddCategoryComponent
  ]
})
export class FormModule {}
