import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategotySelectionComponent } from './categoty-selection/categoty-selection.component';
import { MatSidenavModule,MatToolbarModule,MatIconModule, MatButtonModule  } from '@angular/material';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';




@NgModule({
  declarations: [CategotySelectionComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    CategotySelectionComponent
  ]
})
export class FormModule { }
