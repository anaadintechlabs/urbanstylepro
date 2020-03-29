import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/_service/product/order.service';

@Component({
  selector: 'app-review-order',
  templateUrl: './review-order.component.html',
  styleUrls: ['./review-order.component.scss']
})
export class ReviewOrderComponent implements OnInit {

  constructor(
    public _orderService : OrderService
  ) { }

  ngOnInit(): void {
  }

  minus() {
    if(this._orderService.SinglecheckoutItem.quantity > 1) {
      this._orderService.SinglecheckoutItem.quantity--;
    }
  }

  plus() {
      this._orderService.SinglecheckoutItem.quantity++;
  }

}
