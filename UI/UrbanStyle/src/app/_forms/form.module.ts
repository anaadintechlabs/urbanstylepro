import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategotySelectionComponent } from './categoty-selection/categoty-selection.component';
import { MatSidenavModule,MatToolbarModule,MatIconModule, MatButtonModule, MatStepperModule  } from '@angular/material';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { BasicDetailsComponent } from './basic-details/basic-details.component';
import { LoginComponent } from './login/login.component';




@NgModule({
  declarations: [CategotySelectionComponent, 
    BasicDetailsComponent, 
    LoginComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatStepperModule
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
    LoginComponent
  ]
})
export class FormModule { }
