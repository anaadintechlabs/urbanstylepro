import { Component, OnInit } from '@angular/core';
import { User } from "src/_modals/user.modal";
import { DataService } from "src/_services/data/data.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-sales-return-listing',
  templateUrl: './sales-return-listing.component.html',
  styleUrls: ['./sales-return-listing.component.scss']
})
export class SalesReturnListingComponent implements OnInit {
  user: User;
  userId: any;
  returnList: any = [];
  returnDetails:any;
  selectedReturnId: any;
  constructor(
    public dataService: DataService,
    public _router : Router
  ) { }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id", this.userId);
      this.getAllReturnOfVendor(this.userId);

    }
  }

  chooseAction(data) {
    console.log(data);
    if(!data.f_Status){
      return
    }
    if(data.f_Status == "") {
      return
    }
    if(data.f_Status == "ACCEPT"){
      this.setReturnStatusbyAdmin(data.returnId,'ACCEPT');
      return
    } else if(data.f_Status == 'REJECT'){
      this.setReturnStatusbyAdmin(data.returnId,'REJECT');
    }
  }

  addF_Status(list){
    list.forEach(element => {
      element['f_Status'] = '';
    });
  }

  getOrderProductForVendor(orderProductId,orderId) {
    this._router.navigate(['/vendor/orderDetails',orderProductId,orderId])
  }



  getAllReturnOfVendor(vendorId) {
    let filter={
      'limit':15,
      'offset':0,
      'sortingDirection':'DESC',
      'sortingField':'createdDate'
    }
    this.dataService
      .getAllReturnOfVendor(vendorId, filter,"api/getReturnForVendor")
      .subscribe(
        data => {
          console.log("All order", data);
          this.returnList = data;
          this.addF_Status(this.returnList);
        },
        error => {
          console.log("error======", error);
        }
      );
  }



  // getAllDetailsOfReturn(returnId) {
  //   this.selectedReturnId = returnId;
  //   this.dataService.getOrderProductForVendor(returnId, this.userId, "api/getOrderProductForVendor").subscribe(
  //     data => {
  //       console.log("All Products inside order", data);
  //          this.orderDetails=data.orderDetails;
  //     },
  //     error => {
  //       console.log("error======", error);
  //     }
  //   );
  // }

  getOrderProductForVendor(orderProductId,orderId) {
    this._router.navigate(['/vendor/orderDetails',orderProductId,orderId])
  }



  setReturnStatusbyAdmin(returnId,status) {

    this.dataService.changeStatusOfReturn( returnId,status, "api/setReturnStatusbyAdmin").subscribe(
      data => {
        //instead of this call api for get all order of user
        this.getAllReturnOfVendor(this.userId);

      },
      error => {
        console.log("error======", error);
      }
    );
  }




}
