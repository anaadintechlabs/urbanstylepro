import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/_services/data/data.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/_modals/user.modal';
import {Location} from '@angular/common';

@Component({
  selector: 'app-return-details',
  templateUrl: './return-details.component.html',
  styleUrls: ['./return-details.component.scss']
})
export class ReturnDetailsComponent implements OnInit {
  returnId: any;
  orderProductId:any;
  returnDetails : any;
  orderProduct : any;
  returnFault:any;
  user : User;
  trackingId:any;
  trackingLink:any;
  transactionDetails : any;
  
  constructor(
    public dataService: DataService,
    public _param : ActivatedRoute,
    private _location: Location,
    public toastr:ToastrService
  ) { 
    this._param.params.subscribe(data=>{
      this.returnId = data.returnId;
      this.orderProductId=data.orderProductId;
      console.log(data);
    })
  }
  ngOnInit() {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.getDetails(this.user.id);
    }
  }


    getDetails(userId) {

    this.dataService.getSingleReturnDetails(this.returnId,this.orderProductId, userId, "api/getSingleReturnDetails").subscribe(
      data => {
        console.log("All Products inside order", data);
        //this.orderProduct = data.orderList;
        this.returnDetails=data.returnDetails;
        this.transactionDetails = data.transactionList;
      },
      error => {
        console.log("error======", error);
      }
    );
  }
   backButton()
  {
    this._location.back();
  }


  setTrackingCodeAndUrlForAdmin(returnId)
  {
     this.dataService.setTrackingCodeAndUrlForAdmin(returnId, "api/setTrackingCodeAndUrlForAdmin",this.trackingId,this.trackingLink).subscribe(
      data => {
        console.log(data);
        this.toastr.success("Tracking Code and link added successfuly","Success");
        this.getDetails(this.user.id);
      },
      error => {
        console.log("error======", error);
      }
    );
  }
}
