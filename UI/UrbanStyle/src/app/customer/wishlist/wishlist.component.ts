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

@Component({
  selector: "app-wishlist",
  templateUrl: "./wishlist.component.html",
  styleUrls: ["./wishlist.component.scss"]
})
export class WishlistComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  wishList: any = [];

  public userId: any = 1;
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;

  constructor(
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    //getUserId Dynamic
    this.getWishlistByUser(this.userId);
  }

  ngOnInit() {}

  getWishlistByUser(userId) {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    this.dataService
      .getAllWishListOfUser(
        userId,
        "wishlist/getAllWishListOfUser?userId=" + userId,
        body
      )
      .subscribe(
        data => {
          this.wishList = data;
          this.ELEMENT_DATA = this.wishList;
          this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        },
        error => {
          console.log("error======", error);
        }
      );
  }

  deletewishList(wish) {
    let url =
      "wishlist/softDeleteWishList?userId=" +
      this.userId +
      "&id=" +
      wish.id +
      "&status=0";
    this.dataService.softDeleteWishList(url).subscribe(
      data => {
        this.wishList = data;
        this.ELEMENT_DATA = this.wishList;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
      },
      error => {
        console.log("error======", error);
      }
    );
  }

  pageEvent(event) {}
}
