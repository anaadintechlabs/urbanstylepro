import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { User } from "src/_modals/user.modal";
import { DataService } from "src/_services/data/data.service";
import { PlatformLocation } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-listing',
  templateUrl: './order-listing.component.html',
  styleUrls: ['./order-listing.component.scss']
})
export class OrderListingComponent implements OnInit {

  user: User;
  userId: any;
  showProduct = false;
  orderList: any = [];
  orderProduct: any ;
  orderDetails:any;
  selectedOrderId: any;


  public limit=15;
  public offset=0;
  public sortingField="createdDate";
  public sortingDirection="desc";
  public count;

  status : string;

  constructor(
    public dataService: DataService,
    public _router : Router,
    public location: PlatformLocation,
    public toastr:ToastrService
  ) {
    location.onPopState(() => {
      this.showProduct = false;
      console.log('pressed back!');
  });
   }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id", this.userId);
      this.getAllOrderOfVendor(this.userId);
    }
  }

  chooseAction(data) {
    console.log(data);
    let check=confirm("Are you sure, you want to change the status");
    console.log(check);
    if(check)
    {
    if(!data.f_Status){
      return
    }
    if(data.f_Status == "") {
      return
    }
    if(data.f_Status == "PROGRESS"){
      this.changeStatusOfPartialOrder('INPROGRESS',data.id,data.userOrder.id);
      this.toastr.success("Order marked as INPROGRESS,Take further action","Success")
      // return
    } else if(data.f_Status == 'DISPATCH'){
      this.changeStatusOfPartialOrder('DISPATCHED',data.id,data.userOrder.id);
      this.toastr.success("Order marked as DISPATCHED,Quantity reserved from your inventory","Success")
      // return
    } 
    else if(data.f_Status == 'PLACED'){
      this.changeStatusOfPartialOrder('PLACED',data.id,data.userOrder.id);
      this.toastr.success("Order marked as PLACED,Wait for admin action","Success")
      // return
    } 
    else if(data.f_Status == 'CANCEL'){
      this.cancelOrderByUser(data.id);
      // return
    }
    this.getAllOrderOfVendor(this.userId);

  }
  else{
    setTimeout(() => {
      data.f_Status = "DISABLE";
    }, 0);
  
  }
  }

  getOrderByStatus(status) {
    this.showProduct = false;
    if (status == 'ALL') {
      this.getAllOrderOfVendor(this.userId);
    }
    else {
      this.getAllOrderOfVendorByStatus(this.userId, status);
    }

  }


  getAllOrderOfVendorByStatus(vendorId, status) {

    this.dataService.getAllOrderOfVendorByStatus(vendorId, status, "api/getOrderForVendorByStatus")
      .subscribe(
        data => {
          console.log("All order", data);
          this.orderList = data.orderList;
          //this.count=data.count;

          this.addF_Status(this.orderList);
        },
        error => {
          console.log("error======", error);
        }
      );
  }
  getAllOrderOfVendor(vendorId) {
    let request = {
      "limit":this.limit,
      "offset":0,
      "sortingDirection":this.sortingDirection,
      "sortingField":this.sortingField
    };
    this.dataService
      .getAllOrderOfVendor(vendorId,request, "api/getOrderForVendor")
      .subscribe(
        data => {
          console.log("All order", data);
          this.orderList = data.orderList;
          this.count=data.count;
          //jiust for getting exact page number
          // this.offset+=1;


          this.addF_Status(this.orderList);
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  addF_Status(list){
    list.forEach(element => {
      element['f_Status'] = 'DISABLE';
    });
  }

  backButton() {
    this.showProduct = false;
  }

  getOrderProductForVendor(orderProductId,orderId) {
    this._router.navigate(['/vendor/orderDetails',orderProductId,orderId])
    this.selectedOrderId = orderId;
  }


  changeStatusOfCompleteOrder(status, orderId) {
    this.dataService.changeStatusOfCompleteOrder(status, orderId, "api/setStatusbyVendorForCompleteOrder").subscribe(
      data => {
        this.getAllOrderOfVendor(this.userId);

      },
      error => {
        console.log("error======", error);
      }
    );
  }

  changeStatusOfPartialOrder(status, orderProdId,orderId) {
    console.log("called");
    this.dataService.changeStatusOfPartialOrder(status, orderProdId, "api/setStatusbyVendor",'','').subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log("error======", error);
      }
    );
  }


  cancelOrderByUser(orderId) {
    //this user id will be id of user
    let userId = 1;
    this.dataService.cancelOrderByUser(userId, orderId, "api/cancelOrderByUser").subscribe(
      data => {
        //instead of this call api for get all order of user
        this.getAllOrderOfVendor(this.userId);

      },
      error => {
        console.log("error======", error);
      }
    );
  }

  returnOrderByUser(orderProdId,orderId) {
    //this user id will be id of user
    let userId = 1;
    //prompt reason from user
    let reason = 'bad quality product';
  
    this.dataService.returnOrderByUser(userId, orderId,orderProdId, reason, "api/returnOrderByUser").subscribe(
      data => {
        //instead of this call api for get all order of user
        this.getAllOrderOfVendor(this.userId);

      },
      error => {
        console.log("error======", error);
      }
    );
  }

  completeOrderByAdmin(orderId)
  {
    let userId=1;
    this.dataService.completeOrderByAdmin(userId,orderId,"COMPLETE","api/setStatusbyAdmin").subscribe(
      data => {
      //instead of this call api for get all order of user
      this.getAllOrderOfVendor(this.userId);
      
      },
      error => {
        console.log("error======", error);
      }
    );
  }


  pageChanged(event){
    console.log("page changes"+event)
    this.offset=event-1;
    this.getAllOrderOfVendor(this.userId);
  }
}

