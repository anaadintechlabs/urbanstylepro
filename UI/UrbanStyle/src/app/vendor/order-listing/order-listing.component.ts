import { Component, OnInit } from '@angular/core';
import { User } from "src/_modals/user.modal";
import { DataService } from "src/_services/data/data.service";

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

  public limit=3;
  public offset=0;
  public sortingField="createdDate";
  public sortingDirection="desc";

 


  public count;


  constructor(
    public dataService: DataService
  ) { }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id", this.userId);
      this.getAllOrderOfVendor(this.userId);

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
          this.count=data.count;
         
        },
        error => {
          console.log("error======", error);
        }
      );
  }
  getAllOrderOfVendor(vendorId) {
    let request = {
      "limit":this.limit,
      "offset":this.offset,
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
          this.offset+=1;

        },
        error => {
          console.log("error======", error);
        }
      );
  }

  backButton() {
    this.showProduct = false;
  }

  getOrderProductForVendor(orderProductId,orderId) {
    this.selectedOrderId = orderId;
    this.dataService.getOrderProductForVendor(orderId,orderProductId, this.userId, "api/getOrderProductForVendor").subscribe(
      data => {
        console.log("All Products inside order", data);
        this.showProduct = true;
        //now single product
        this.orderProduct = data.orderList;
        this.orderDetails=data.orderDetails;
        console.log("Address ",this.orderDetails)
      },
      error => {
        console.log("error======", error);
      }
    );
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
    this.dataService.changeStatusOfPartialOrder(status, orderProdId, "api/setStatusbyVendor").subscribe(
      data => {
        this.getOrderProductForVendor(orderProdId,orderId);

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

