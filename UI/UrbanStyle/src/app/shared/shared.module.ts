import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CurrencyFormatPipe } from './pipes/currency-format.pipe';
import { ActionHandlerComponent } from './component/action-handler/action-handler.component';



@NgModule({
  declarations: [
    ActionHandlerComponent,
    CurrencyFormatPipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports : [
    ActionHandlerComponent,
    CurrencyFormatPipe
  ]
})
export class SharedModule { }
