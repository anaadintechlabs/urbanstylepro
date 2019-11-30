import { Component, OnInit, ViewChild } from "@angular/core";
import { WishList } from "src/_modals/wishList";

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
import { User } from "src/_modals/user.modal";
@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  @ViewChild(MatSort, { static: true })
  sort: MatSort;
  public userId: any = 1;
  productList: any = [];
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 15;
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
    this.getproductList();
  }

  ngOnInit() {}

  getproductList() {
    this.dataService.getproductListDummy("product/getAllProducts").subscribe(
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

  addProductToWishlist(productVar) {
    let url = "wishlist/addProductToWishlist";

    // const userObj: User = {
    //   id: this.userId
    // };
    const wishListObj: WishList = {
      id: 0,
      productVariant: productVar,
      user: undefined,
      status: 1
    };

    this.dataService.addProductToWishlist(url, wishListObj).subscribe(
      data => {
        if (data.alreadyAdded) {
          alert("already added");
        } else {
          alert("added to wishlist");
        }
      },
      error => {
        console.log("error======", error);
      }
    );
  }
}
