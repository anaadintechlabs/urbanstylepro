import { Component, OnInit } from '@angular/core';
import { User } from "src/_modals/user.modal";
import { DataService } from "src/_services/data/data.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-sales-listing',
  templateUrl: './sales-listing.component.html',
  styleUrls: ['./sales-listing.component.scss']
})
export class SalesListingComponent implements OnInit {

  user: User;
  userId: any;
  showProduct=false;
  salesList:any=[];
  salesProductList:any=[];
  selectedOrderId: any;
   public limit=15;
  public offset=0;
  public sortingField="createdDate";
  public sortingDirection="desc";
  public count;

  constructor(
    public dataService:DataService,
    public _router : Router
  ) { }

  ngOnInit() {
     this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id",this.userId);
      this.getAllSalesOfVendor(this.userId);
    }
  }

  getOrderProductForVendor(orderProductId,orderId) {
    this._router.navigate(['/vendor/orderDetails',orderProductId,orderId])
  }

  getAllSalesOfVendor(vendorId){
    let request = {
      "limit":this.limit,
      "offset":0,
      "sortingDirection":this.sortingDirection,
      "sortingField":this.sortingField
    };
     this.dataService
      .getAllOrderOfVendorByStatus( vendorId,'COMPLETE',request, "api/getOrderForVendorByStatus")
      .subscribe(
        data => {
         console.log("All order",data);
         this.salesList=data.orderList;
         this.count=data.count;
        },
        error => {
          console.log("error======", error);
        }
      );
  }

}
