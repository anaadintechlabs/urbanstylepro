import { Component, OnInit } from '@angular/core';
import { User } from "src/_modals/user.modal";
import { DataService } from "src/_services/data/data.service";

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
  constructor(public dataService:DataService) { }

  ngOnInit() {
     this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id",this.userId);
      this.getAllSalesOfVendor(this.userId);

    }
  }




 
  getAllSalesOfVendor(vendorId)
  {
     this.dataService
      .getAllOrderOfVendorByStatus( vendorId,'COMPLETE', "api/getOrderForVendorByStatus")
      .subscribe(
        data => {
         console.log("All order",data);
         this.salesList=data;
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
    this.selectedOrderId=orderId;
    this.dataService.getOrderProductForVendor(orderId,1,this.userId,"api/getOrderProductForVendor").subscribe(
        data => {
         console.log("All Products inside order",data);
         this.showProduct=true;
         this.salesProductList=data;
        },
        error => {
          console.log("error======", error);
        }
      );
  }



}
