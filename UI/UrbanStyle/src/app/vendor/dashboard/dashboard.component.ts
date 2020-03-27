import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/_services/data/data.service';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  public vendorList: any[]=[];
  public vendorId : number;
  public lastOrdersList: any[];
  public returnsList: any[];
  public lastSalesList: any[];
  public lastReviewList: any[];
  public wallet: any;

  public filter = {
    "limit":5,
    "offset":0,
    "sortingDirection":"desc",
    "sortingField":"createdDate"

  }

  constructor(private service: DataService, private userService: UserServiceService) { }

  ngOnInit() {
    this.getVendorId();
    // this.getLastOrderList();
    this.getLastReviewList();
  }

  getVendorId() {
    this.vendorId = Number(JSON.parse(this.userService.getUser()).id);
    console.log(this.vendorId);
  }

  // getLastOrderList() {
  //   let url = 'api/getLastOrdersForVendor';
  //   this.service.getLastOrdersForVendor(url, 0, this.vendorId, 'PLACED').subscribe(
  //     data => {
  //       this.lastOrdersList = data;
  //     }, error => {
  //       console.log(error);
  //     }
  //   )
  // }

  // getReturnsList() {
  //   let url = 'api/getReturnForVendor';
  //   this.service.getReturnForVendor(url, this.vendorId, this.filter).subscribe(
  //     data => {
  //       this.returnsList = data;
  //       console.log(this.returnsList);
  //     }, error => {
  //       console.log(error);
  //     }
  //   )
  // }


  // getSalesList() {
  //   let url = 'api/getLastOrdersForVendor';
  //   this.service.getLastOrdersForVendor(url, 0, this.vendorId, 'COMPLETE').subscribe(
  //     data => {
  //       this.lastSalesList = data;
  //       console.log(this.lastSalesList);
  //     }, error => {
  //       console.log(error);
  //     }
  //   )
  // }

  getLastReviewList() {
    let url = 'review/getLast5ProductReviewsOfVendor';
    this.service.getLast5ProductReviewsOfVendor(url, 0, this.vendorId).subscribe(
      data => {
        this.lastReviewList = data;
        console.log(this.lastReviewList);
      }, error => {
        console.log(error);
      }
    )
  }


  // getUserWallet() {
  //   let url = 'api/getWalletByUser';
  //   this.service.getWalletByUser(url, this.vendorId).subscribe(
  //     data => {
  //       this.wallet = data;
  //       console.log(this.wallet);
  //     }, error => {
  //       console.log(error);
  //     }
  //   )
  // }


}
