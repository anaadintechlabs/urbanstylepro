import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActionHandlerComponent } from './action-handler.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    ActionHandlerComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports : [
    ActionHandlerComponent
  ]
})
export class SharedModule { }
