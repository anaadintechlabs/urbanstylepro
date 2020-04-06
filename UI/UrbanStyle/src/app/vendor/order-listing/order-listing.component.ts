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
  filterSelected: boolean;
  selectdStatus: any;
  pageNumber=1;
  timeRange='';
  user: User;
  userId: any;
  showProduct = false;
  orderList: any = [];
  orderProduct: any ;
  orderDetails:any;
  selectedOrderId: any;


  public limit=5;
  public offset=0;
  public sortingField="id";
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
  });
   }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      this.getAllOrderOfVendor(this.userId);
    }
  }

  chooseAction(data) {
        let check = confirm("Are you sure, you want to change the status");
    if (check) {
      if (!data.f_Status) {
        return
      }
      if (data.f_Status == "") {
        return
      }
      if (data.f_Status == "INPROGRESS") {
        this.changeStatusOfPartialOrder('INPROGRESS', data.orderProductId, data.orderId);
        this.toastr.success("Order marked as INPROGRESS,Take further action", "Success")

        // return
      } else if (data.f_Status == 'DISPATCHED') {
        this.changeStatusOfPartialOrder('DISPATCHED', data.orderProductId, data.orderId);
        this.toastr.success("Order marked as DISPATCHED,Quantity reserved from your inventory", "Success")

        // return
      }
      else if (data.f_Status == 'PLACED') {
        this.changeStatusOfPartialOrder('PLACED', data.orderProductId, data.orderId);
        this.toastr.success("Order marked as PLACED,Wait for admin action", "Success")

      }
      else if (data.f_Status == 'CANCEL') {
        this.cancelOrderByUser(data.orderProductId, data.orderId, data.userId);
        // return
      }
      else if (data.f_Status == 'RETURN') {
        this.returnOrderByUser(data.orderProductId, data.orderId, data.userId);
        // return
      }


     }

    else {
      setTimeout(() => {
        data.f_Status = "DISABLE";
      }, 0);
 
    }
  }

  getOrderByStatus(status) {
    this.selectdStatus=status;
    this.filterSelected=true;
    this.showProduct = false;
    if (status == 'ALL') {
      this.getAllOrderOfVendor(this.userId);
    }
    else {
      this.getAllOrderOfVendorByStatus(this.userId, status);
    }

  }


  getAllOrderOfVendorByStatus(vendorId, status) {

    let request = {
      "limit":this.limit,
      "offset":0,
      "sortingDirection":this.sortingDirection,
      "sortingField":this.sortingField
    };

    this.dataService.getAllOrderOfVendorByStatus(vendorId, status,request, "api/getOrderForVendorByStatus")
      .subscribe(
        data => {
          console.log("All order", data);
          this.orderList = data.orderList;
          this.count=data.count;

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
          // this.offset=1;


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
        this.getAllOrderOfVendor(this.userId);
      },
      error => {
        console.log("error======", error);
      }
    );
  }


  cancelOrderByUser(orderProductId,orderId,userId) {
    //this user id will be id of user
    //let userId = 1;
    this.dataService.cancelOrderByUser(userId, orderId,orderProductId, "api/cancelOrderByUser").subscribe(
      data => {
        //instead of this call api for get all order of user
        this.toastr.success("Order cancelled Successfully");
        this.getAllOrderOfVendor(this.userId);

      },
      error => {
        console.log("error======", error);
      }
    );
  }

  returnOrderByUser(orderProductId,orderId,userId) {
    //this user id will be id of user
    //prompt reason from user
    let reason = 'bad quality product';
  
    this.dataService.returnOrderByUser(userId, orderId,orderProductId, reason, "api/returnOrderByUser").subscribe(
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
    this.offset=event-1;
    this.pageNumber=event;
    this.getAllOrderOfVendor(this.userId);
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
    if(this.filterSelected)
      {
        if (this.selectdStatus == 'ALL') {
      this.getAllOrderOfVendor(this.userId);
    }
    else{
        this.getAllOrderOfVendorByStatus(this.userId,this.selectdStatus);
    }
      }
      else
        {
          
      this.getAllOrderOfVendor(this.userId);
        }
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

};



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
  if (this.filterSelected) {
      if (this.selectdStatus == 'ALL') {
        this.getAllOrderOfVendorWithRequest(this.userId,request);
      }
      else {
        this.getAllOrderOfVendorByStatusWithRequest(this.userId, this.selectdStatus,request);
      }
    }
    else {

      this.getAllOrderOfVendorWithRequest(this.userId,request);
    } 

      }
  }




    getAllOrderOfVendorByStatusWithRequest(vendorId, status,request) {


    this.dataService.getAllOrderOfVendorByStatus(vendorId, status, request, "api/getOrderForVendorByStatus")
      .subscribe(
      data => {
        console.log("All order", data);
        this.orderList = data.orderList;
        this.count=data.count;

        this.addF_Status(this.orderList);
      },
      error => {
        console.log("error======", error);
      }
      );
  }
  getAllOrderOfVendorWithRequest(vendorId,request) {
 
    this.dataService
      .getAllOrderOfVendor(vendorId, request, "api/getOrderForVendor")
      .subscribe(
      data => {
        console.log("All order", data);
        this.orderList = data.orderList;
        this.count = data.count;
        this.addF_Status(this.orderList);
      },
      error => {
        console.log("error======", error);
      }
      );
  }


}

