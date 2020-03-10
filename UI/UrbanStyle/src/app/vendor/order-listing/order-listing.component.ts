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
  showProduct=false;
  orderList:any=[];
  orderProductList:any=[];
  constructor(public dataService:DataService) { }

  ngOnInit() {
     this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id",this.userId);
      this.getAllOrderOfVendor(this.userId);

    }
  }

  getAllOrderOfVendor(vendorId)
  {
     this.dataService
      .getAllOrderOfVendor( vendorId, "api/getOrderForVendor")
      .subscribe(
        data => {
         console.log("All order",data);
         this.orderList=data;
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  backButton()
  {
    this.showProduct=false;
  }

  getOrderProductForVendor(orderId)
  {
    this.dataService.getOrderProductForVendor(orderId,this.userId,"api/getOrderProductForVendor").subscribe(
        data => {
         console.log("All Products inside order",data);
         this.showProduct=true;
         this.orderProductList=data;
        },
        error => {
          console.log("error======", error);
        }
      );
  }
}

  