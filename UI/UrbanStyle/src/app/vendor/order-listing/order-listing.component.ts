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
  orderProductList: any ;
  orderDetails:any;
  selectedOrderId: any;
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
          this.orderList = data;
        },
        error => {
          console.log("error======", error);
        }
      );
  }
  getAllOrderOfVendor(vendorId) {
    this.dataService
      .getAllOrderOfVendor(vendorId, "api/getOrderForVendor")
      .subscribe(
        data => {
          console.log("All order", data);
          this.orderList = data;
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  backButton() {
    this.showProduct = false;
  }

  getOrderProductForVendor(orderId) {
    this.selectedOrderId = orderId;
    this.dataService.getOrderProductForVendor(orderId, this.userId, "api/getOrderProductForVendor").subscribe(
      data => {
        console.log("All Products inside order", data);
        this.showProduct = true;
        this.orderProductList = data.orderList;
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

  changeStatusOfPartialOrder(status, orderProdId) {
    this.dataService.changeStatusOfPartialOrder(status, orderProdId, "api/setStatusbyVendor").subscribe(
      data => {
        this.getOrderProductForVendor(this.selectedOrderId);

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

  returnOrderByUser(orderId) {
    //this user id will be id of user
    let userId = 1;
    //prompt reason from user
    let reason = 'bad quality product';
    this.dataService.returnOrderByUser(userId, orderId, reason, "api/returnOrderByUser").subscribe(
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
}

