import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { User } from 'src/_modals/user.modal';
@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"]
})
export class InventoryComponent implements OnInit {

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

  @ViewChild('variant',{static: false}) modal;

  constructor(
    private dataService: DataService,
  ) {
    //getUserId Dynamic
    this.user =  JSON.parse(window.localStorage.getItem('user'));
    if(this.user.token){
      this.userId = this.user.id;

      // this.getAllProductVariantOfUser();
      this.getAllProductOfUser();
    } 
  }

  ngOnInit() {}

  getAllProductOfUser() {
    let body = {
      "limit":15,
      "offset":0,
      "sortingDirection":"DESC",
      "sortingField":"modifiedDate"
    }
    this.dataService.getAllProductOfUser(this.userId,body).subscribe(data=> {
      this.productList = data.productList;
    })
  }

  getVariationBasedOnProductId(productId)
  {
    
  }

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
        },
        error => {
          console.log("error======", error);
        }
      );
      console.log(this.productList);
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
      },
      error => {
        console.log("error======", error);
      }
    );
    console.log(this.productList);
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
      },
      error => {
        console.log("error======", error);
      }
    );
    console.log(this.productList);
  }

  getAllVeriant(item) {
    console.log(item);
    this.modal.open();
  }

  pageEvent(event) {}
}
