import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../../../../_service/product/order.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'
  public orderList:any;
  public orderDetails:any;
  constructor(public orderService:OrderService,public _router : Router) { }

  ngOnInit() {
    this.getOrderOfUser();
  }

  getOrderOfUser()
  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.orderService.getOrderOfUser(filter).subscribe(data=>{
      console.log("data us",data);
      this.orderList=data.data.orderList;
    },error=>{
      console.log("error",error);
    })
  }


  cancelOrderByUser(orderId,orderProductId)
  {
    let check =confirm("Are you sure you want to cancel this offer");
    if(check)
    {
    this.orderService.cancelOrderByUser(orderId,orderProductId).subscribe(data=>{
     this.getOrderOfUser();
    },error=>{
      console.log("error",error);
    })
  }
  }


    getOrderProductForVendor(orderProductId,orderId) {
    this._router.navigate(['account/dashboard/orderDetails',orderProductId,orderId])
  }


  returnOrderByUserPopUp(orderId,orderProdId)
  {
    
  }
  returnOrderByUser(orderId,orderProdId,reason)
  {
    this.orderService.returnOrderByUser(orderId,orderProdId,reason).subscribe(data=>{
      this.getOrderOfUser();
     },error=>{
       console.log("error",error);
     }) 
  }


  getOrderById(userOrderId)
  {
    this.orderService.getOrderById(userOrderId).subscribe(data=>{
      this.orderDetails=data.orderDetails;
     },error=>{
       console.log("error",error);
     }) 
  }

}
