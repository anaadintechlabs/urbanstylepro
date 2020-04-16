import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { User } from "src/_modals/user.modal";
import { UserService } from "src/_services/http_&_login/user-service.service";
@Component({
  selector: 'app-sales-history',
  templateUrl: './sales-history.component.html',
  styleUrls: ['./sales-history.component.scss']
})
export class SalesHistoryComponent implements OnInit {


  public offset=0;
  public limit=15;
  public sortingField="id";
  public sortingDirection="desc";
  public count=10;
  pageNumber=1;
  timeRange='';

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
      'offset':this.offset,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
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



  pageChanged(event){
    this.offset=event-1;
    this.pageNumber=event;
    this.getAllReturnOfAffiliate(this.userId);
  }

  sortHeaderClick(sortinField)
  {
            if(this.sortingDirection=='asc')
          {
            this.sortingDirection='desc';
          }
          else
            {
              this.sortingDirection='asc';
            }
    this.sortingField=sortinField;
    this.getAllReturnOfAffiliate(this.userId);
  }

  isSorting(name: string) {
  return this.sortingField !== name && name !== '';
};
 
isSortAsc(name: string) {
  if(this.sortingField === name && this.sortingDirection === 'asc')
    {
  return true;
    }

};
 
isSortDesc(name: string) {
  if(this.sortingField === name && this.sortingDirection === 'desc')
    {
  return true;
    }
  }


  chooseDateRange() {
    if(this.timeRange!='')
      {
    let dte = new Date();
    if (this.timeRange == 'WEEKLY') {
      dte.setDate(dte.getDate() - 7);
    }
    if (this.timeRange == 'MONTHLY') {
      dte.setDate(dte.getDate() - 30);
    }
    if (this.timeRange == 'QUARTERLY') {
      dte.setDate(dte.getDate() - 90);
    }

    let dateString = dte.getTime()  +','+  new Date().getTime();
       let request = {
      "limit": this.limit,
      "offset": this.offset,
      "sortingDirection": this.sortingDirection,
      "sortingField": this.sortingField,
      "dateRange":dateString
    };
    this.userService
      .getAllReturnOfAffiliate(this.userId, request)
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



}