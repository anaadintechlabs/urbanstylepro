import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { UserService } from "src/_services/http_&_login/user-service.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-shared-products',
  templateUrl: './shared-products.component.html',
  styleUrls: ['./shared-products.component.scss']
})
export class SharedProductsComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='orderdate'
  public orderList:any;
  public orderDetails:any;
  constructor(public userService:UserService,public _router : Router,public toastr:ToastrService,) { }

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
    this.userService.getOrderOfUser(filter).subscribe(data=>{
      console.log("data us",data);
      this.orderList=data.data.orderList;
    },error=>{
      console.log("error",error);
    })
  }




    getOrderProductForVendor(vendorId,orderProductId,orderId) {
    this._router.navigate(['account/dashboard/orderDetails',vendorId,orderProductId,orderId])
  }


}
