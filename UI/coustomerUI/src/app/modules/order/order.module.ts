import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderComponent } from './order.component';
import { OrderRoutingModule } from './order.routing';
import { BillingDetailsComponent } from './components/billing-details/billing-details.component';
import { ReviewOrderComponent } from './components/review-order/review-order.component';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { BankDetailsComponent } from './components/bank-details/bank-details.component';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [OrderComponent, BillingDetailsComponent, ReviewOrderComponent, ConfirmationComponent, BankDetailsComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    OrderRoutingModule,
    SharedModule
  ]
})
export class OrderModule { }
