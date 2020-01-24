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
import { User } from 'src/_modals/user.modal';
@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"]
})
export class InventoryComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  productList: any = [];
  bankDetailCount: number;
  public userId: any = 1;
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
    private router: Router
  ) {
    //getUserId Dynamic
    this.user =  JSON.parse(window.localStorage.getItem('user'));
    if(this.user.token){
      this.userId = this.user.id;

      this.getAllProductVariantOfUser();
    } 
  }

  ngOnInit() {}

  getAllProductVariantOfUser() {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    this.dataService
      .getAllProductVariantOfUser("product/getAllProductVariantOfUser?userId=" + this.userId, body)
      .subscribe(
        data => {
          console.log("datais ",data);
          this.productList = data;
          this.ELEMENT_DATA = this.productList;
          this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  getAllActiveOrInactiveProductVariantOfUser(status) {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    let url =
      "product/getAllActiveOrInactiveProductVariantOfUser?userId=" +
      this.userId +
      "&status=" +
      status;
    this.dataService.getAllActiveOrInactiveProductVariantOfUser(url, body).subscribe(
      data => {
        this.productList = data;
        this.ELEMENT_DATA = this.productList;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
      },
      error => {
        console.log("error======", error);
      }
    );
  }

  changeStatusOfProductVariant(productId, status) {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    let url =
      "product/changeStatusOfProductVariant?userId=" +
      this.userId +
      "&productId=" +
      productId +
      "&status=" +
      status;
    this.dataService.changeStatusOfProductVariant(url, body).subscribe(
      data => {
        this.productList = data;
        this.ELEMENT_DATA = this.productList;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
      },
      error => {
        console.log("error======", error);
      }
    );
  }

  pageEvent(event) {}
}
