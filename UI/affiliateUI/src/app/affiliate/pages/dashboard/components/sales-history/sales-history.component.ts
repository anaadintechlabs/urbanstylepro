import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { UserService } from "src/_services/http_&_login/user-service.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { User } from "src/_modals/user.modal";
@Component({
  selector: 'app-sales-history',
  templateUrl: './sales-history.component.html',
  styleUrls: ['./sales-history.component.scss']
})
export class SalesHistoryComponent implements OnInit {

   user: User;
  userId: any;
  returnList: any = [];
  returnDetails:any;
  selectedReturnId: any;
  constructor(
   public userService:UserService,public _router : Router,public toastr:ToastrService
  ) { }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      this.getAllReturnOfAffiliate(this.userId);

    }
  }



  // addF_Status(list){
  //   list.forEach(element => {
  //     element['f_Status'] = 'DISABLE';
  //   });
  // }

 getOrderProductForVendor(vendorId,orderProductId,orderId) {
    this._router.navigate(['account/dashboard/orderDetails',vendorId,orderProductId,orderId])
  }




  getAllReturnOfAffiliate(affiliateId) {
    let filter={
      'limit':15,
      'offset':0,
      'sortingDirection':'DESC',
      'sortingField':'orderdate'
    }
    this.userService
      .getAllReturnOfAffiliate(affiliateId, filter)
      .subscribe(
        data => {
          console.log("All order", data);
          this.returnList = data.data.returnList;
        //  this.addF_Status(this.returnList);
          
        },
        error => {
          console.log("error======", error);
        }
      );
  }








}
