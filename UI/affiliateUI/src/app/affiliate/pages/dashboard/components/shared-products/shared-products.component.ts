import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { UserService } from "src/_services/http_&_login/user-service.service";

@Component({
  selector: 'app-shared-products',
  templateUrl: './shared-products.component.html',
  styleUrls: ['./shared-products.component.scss']
})
export class SharedProductsComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingField="id";
  public sortingDirection="desc";
  public count=10;
  pageNumber=1;
  timeRange='';
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

  pageChanged(event){
    this.offset=event-1;
    this.pageNumber=event;
    this.getOrderOfUser();
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
    this.getOrderOfUser();
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
    getOrderProductForVendor(vendorId,orderProductId,orderId) {
    this._router.navigate(['account/dashboard/orderDetails',vendorId,orderProductId,orderId])
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
    this.userService.getOrderOfUser(request).subscribe(data=>{
      console.log("data us",data);
      this.orderList=data.data.orderList;
    },error=>{
      console.log("error",error);
    })

      }
  }

}