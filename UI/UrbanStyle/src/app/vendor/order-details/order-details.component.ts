import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/_services/data/data.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/_modals/user.modal';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {
  
  orderDetails : any;
  orderProduct : any;
  orderId : any;
  orderProductId: any;
  user : User;

  constructor(
    public dataService: DataService,
    public _param : ActivatedRoute
  ) { 
    this._param.params.subscribe(data=>{
      this.orderId = data.orderId
      this.orderProductId = data.productId
      console.log(data);
    })
  }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.getDetails(this.user.id);
    }
  }

  getDetails(userId) {
    console.log(this.orderId);
    console.log(this.orderProductId);
    this.dataService.getOrderProductForVendor(this.orderId,this.orderProductId, userId, "api/getOrderProductForVendor").subscribe(
      data => {
        console.log("All Products inside order", data);
        this.orderProduct = data.orderList;
        this.orderDetails=data.orderDetails;
        console.log("Address ",this.orderDetails)
      },
      error => {
        console.log("error======", error);
      }
    );
  }

}
