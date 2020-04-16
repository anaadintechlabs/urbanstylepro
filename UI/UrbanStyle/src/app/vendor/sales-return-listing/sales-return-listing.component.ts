import { ToastrService } from 'ngx-toastr';
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
  selectedType:any='CUSTOMER_RETURN';

  pageNumber=1;
  timeRange='';
  public limit=5;
  public offset=0;
  public sortingField="id";
  public sortingDirection="desc";
  public count=10;

  constructor(
    public dataService: DataService,
    public _router : Router,
    public toastr:ToastrService
  ) { }

  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id", this.userId);
      this.getAllReturnOfVendor('CUSTOMER_RETURN');

    }
  }

  chooseAction(data) {
    console.log(data);
    let check=confirm("Are you sure, you want to change the status");
    if(check)
    {
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
    else if(data.f_Status == 'RECIEVED')
      {
this.setReturnStatusbyAdmin(data.returnId,'RECIEVED');
      }
  }
  else
  {
    setTimeout(() => {
      data.f_Status = "DISABLE";
    }, 0);
  }
  }

  addF_Status(list){
    list.forEach(element => {
      element['f_Status'] = 'DISABLE';
    });
  }

  getOrderProductForVendor(orderProductId,returnId) {
    this._router.navigate(['/vendor/returnDetails',returnId,orderProductId]);
  }



  getAllReturnOfVendor(type) {
    this.selectedType=type;
    let filter={
      'limit':this.limit,
      "offset": this.offset,
      "sortingDirection": this.sortingDirection,
      "sortingField": this.sortingField,
    }
    this.dataService
      .getAllReturnOfVendor(this.userId, filter,type,"api/getReturnForVendor")
      .subscribe(
        data => {
          console.log("All order", data);
          this.returnList = data;
          this.addF_Status(this.returnList);
          this.count = this.returnList.count;
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


  pageChanged(event){
    this.offset=event-1;
    this.pageNumber=event;
    this.getAllReturnOfVendor('CUSTOMER_RETURN');
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
    this.getAllReturnOfVendor('CUSTOMER_RETURN');
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


  setReturnStatusbyAdmin(returnId,status) {

    this.dataService.changeStatusOfReturn( returnId,status, "api/setReturnStatusbyAdmin").subscribe(
      data => {
        //instead of this call api for get all order of user
        this.toastr.success("Status changed successfully","Success");

        this.getAllReturnOfVendor(this.selectedType);
        
      },
      error => {
        console.log("error======", error);
      }
    );
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
    this.dataService
    .getAllReturnOfVendor(this.userId, request,"CUSTOMER_RETURN","api/getReturnForVendor")
    .subscribe(
      data => {
        console.log("All order", data);
        this.returnList = data;
        this.addF_Status(this.returnList);
        this.count = this.returnList.count;
      },
      error => {
        console.log("error======", error);
      }
    );

      }
  }




}