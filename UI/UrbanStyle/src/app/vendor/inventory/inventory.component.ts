import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { User } from 'src/_modals/user.modal';
import { ApiService } from 'src/_services/http_&_login/api.service';
import { isNgTemplate } from '@angular/compiler';
@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"]
})
export class InventoryComponent implements OnInit {

  productList: any[] = [];
  filterList : any[] = [];
  variantList : any[] = [];

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
  private filter : search;

  constructor(
    private dataService: DataService,
    private _apiService : ApiService
  ) {
    //getUserId Dynamic
    this.user =  JSON.parse(window.localStorage.getItem('user'));
    if(this.user.token){
      this.userId = this.user.id;
      this.getAllProductOfUser();
    } 
  }

  ngOnInit() {
    this.filter = {
      search : "",
      grtPrice : "", 
      lessPrice : "",
      grtDate : "",
      lessDate : "",
      status : null,
      sortField : "",
      sortdir : "",
      limit : 50,
      offset : 0
    }
  }

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

  getFilterProduct() {
    this._apiService.post('/product/searchInventory',this.filter).subscribe(res=>{
      if(res.isSuccess){
        this.filterList = res.data;
      }
    })
  }

  getAllInactiveVariant(){
    this.filter.status = 0;
    this.getFilterProduct();
  }
  
  getAllActiveVariant(){
    this.filter.status = 1;
    this.getFilterProduct();
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

  getAllProductOfVarient(item) {
   
    this._apiService.get('product/getAllVarientsOfProducts?prodId='+item.productId).subscribe(res=>{
      if(res.isSuccess){
        console.log(res.product)
        this.variantList = res.data.product;
      }
    })
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

    let url = "product/changeStatusOfProductVariant?userId=" +
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
    this.getAllProductOfVarient(item);
    this.modal.open();
  }

  pageEvent(event) {}
}

interface search {
  search : string;
  grtPrice : string; 
	lessPrice : string;
	grtDate : string;
	lessDate : string;
	status : number;
	sortField : string;
	sortdir : string;
	limit : number;
	offset : number;
}
