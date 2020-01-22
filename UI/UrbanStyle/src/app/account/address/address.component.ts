import { Component, OnInit, ViewChild } from "@angular/core";

import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatPaginator,
  MatSort,
  MatTableDataSource
} from "@angular/material";
import { DataService } from "src/_services/data/data.service";
import { Router, ActivatedRoute } from "@angular/router";
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
import { User } from 'src/_modals/user.modal';

@Component({
  selector: "app-address",
  templateUrl: "./address.component.html",
  styleUrls: ["./address.component.scss"]
})
export class AddressComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  displayedColumns: string[] = ["group", "groupDescription", "actions"];
  addressDetails: any = [];
  addressCount: number;
  public userId: number;
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;
  user : User;

  constructor(
        private dataService: DataService,
        private route: ActivatedRoute,
        private router: Router,
        private _userService : UserServiceService
  ) {
    //getUserId Dynamic
    this.user =  JSON.parse(window.localStorage.getItem('user'));
    if(this.user.token){
      this.userId = this.user.id;
      console.log(this.user);
      console.log(this.userId);
      this.getAddressDetailsByUser(this.userId);
    } 
  }

  ngOnInit() {}

  getAddressDetailsByUser(userId) {
    this.dataService
      .getAddressDetailsByUser(
        userId,
        "api/getAddressDetailsByUser?userId=" + userId
      )
      .subscribe(
        data => {
          this.addressDetails = data;
          this.ELEMENT_DATA = this.addressDetails;
          this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  navigateToEditOrViewAddress(action, address) {
    if (this.userId) {
      this.router.navigateByUrl(
        "vendor/account/addresses/"+action+
          "/" +
          address.id
      );
    }
  }

  deleteAddressDetails(address) {
    let url =
      "api/deleteAddressDetails?userId=" +
      this.userId +
      "&addressId=" +
      address.id +
      "&status=0";
    this.dataService.deleteAddressDetails(url).subscribe(
      data => {
        this.addressDetails = data;
        this.ELEMENT_DATA = this.addressDetails;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
      },
      error => {
        console.log("error======", error);
      }
    );
  }

  navigateToAddGroup() {
    if (this.userId) {
      this.router.navigateByUrl(
        "vendor/account/addresses/add"
      );
    }
  }


  pageEvent(event) {
   
  }
}
