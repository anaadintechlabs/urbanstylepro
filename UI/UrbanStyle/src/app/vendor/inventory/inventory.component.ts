import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { User } from 'src/_modals/user.modal';
import { ApiService } from 'src/_services/http_&_login/api.service';
import { isNgTemplate } from '@angular/compiler';
import { AddProductService } from 'src/_services/product/addProductService';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"]
})
export class InventoryComponent implements OnInit {
  public showProducts: boolean = true;
  public showVariants: boolean;
  productList: any[] = [];
  filterList: any[] = [];
  variantList: any[] = [];

  bankDetailCount: number;
  public userId: any = 1;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;
  user: User;

  @ViewChild("variant", { static: false })
  modal;
  private filter: search;
  showSale : boolean = false;

  constructor(
    private dataService: DataService,
    private _apiService: ApiService,
    public _addProduct: AddProductService,
    private _router: Router,
    private toastr: ToastrService
  ) {
    //getUserId Dynamic
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user.token) {
      this.userId = this.user.id;
      this.getAllProductOfUser();
    }
  }

  bulkOption : string[] = ['Active','Inctive','Delete']

  ngOnInit() {
    this.filter = {
      search: "",
      grtPrice: "",
      lessPrice: "",
      grtDate: "",
      lessDate: "",
      status: null,
      sortField: "",
      sortdir: "",
      limit: 50,
      offset: 0
    };
  }

  selectedVarientForBulkAction : any[]  = [];
  checkboxChangeEvent(ev:any,data:any) {
    if(ev.target.checked) {
      // console.log(ev.target.checked);
      // console.log(data);
      this.selectedVarientForBulkAction.push(data);
    } else {
      let i = this.selectedVarientForBulkAction.indexOf(data);
      this.selectedVarientForBulkAction.splice(i,1);
    }
    console.log(this.selectedVarientForBulkAction);
  }

  bulkAction(value) {
    console.log(value);
  }

  saleStatus() {
    this.showSale = !this.showSale;
  }

  getAllProductOfUser() {
    let body = {
      limit: 25,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };
    this.dataService.getAllProductOfUser(this.userId, body).subscribe(data => {
      this.productList = data.productList;
      this.showProducts = true;
      this.showVariants = false;
    });
  }

  getFilterProduct() {
    this._apiService
      .post("/product/searchInventory", this.filter)
      .subscribe(res => {
        if (res.isSuccess) {
          this.filterList = res.data;
        }
      });
  }

  getAllInactiveVariant() {
    this.filter.status = 0;
    this.getFilterProduct();
  }

  getAllActiveVariant() {
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
      .getAllProductVariantOfUser(
        "product/getAllProductVariantOfUser?userId=" + this.userId,
        body
      )
      .subscribe(
        data => {
          console.log("datais ", data);
          this.showProducts = false;
          this.showVariants = true;
          this.productList = data;
        },
        error => {
          console.log("error======", error);
        }
      );
    console.log(this.productList);
  }

  getAllProductOfVarient(item) {
    this._apiService
      .get("product/getAllVarientsOfProducts?prodId=" + item.productId)
      .subscribe(res => {
        if (res.isSuccess) {
          console.log(res.product);
          this.variantList = res.data.product;
          item['child'] = this.variantList;
        }
      });
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
    this.dataService
      .getAllActiveOrInactiveProductVariantOfUser(url, body)
      .subscribe(
        data => {
          this.productList = data;
          this.showProducts = false;
          this.showVariants = true;
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
    // this.modal.open();
  }

  updatePrices(updatedDTO) {
    console.log(updatedDTO);
    let obj = [
      {
        productVariantId: updatedDTO.productVariantId,
        actualPrice: updatedDTO.actualPrice,
        displayPrice: updatedDTO.displayPrice
      }
    ];
    let url = "product/updateVarientDTO";
    this.dataService.updateVarientDTO(url, obj).subscribe(
      data => {
        this.toastr.success("Prices updated successfully");
      },
      error => {
        console.log("error======", error);
         this.toastr.warning("Something went wrong");
      }
    );
  }

  pageEvent(event) {}

  goToEdit(item) {
    // this._router.navigate(['edit', item.productId, 0])
    this._addProduct.productStatus = "EDIT";
    this._apiService
      .post(`product/getCompleteProduct?prodId=${item.productId}`)
      .subscribe(res => {
        if (res.isSuccess) {
          this._addProduct.selectedCatID = res.data.productList.product.categoryId
          console.log(res.data.productList);
          this._addProduct.productFormGroup.patchValue(
            res.data.productList.product
          );
          this._addProduct.productVariantDTO.clear();
          if(res.data.productList.productVariantDTO.length == 1){
             if(Object.keys(res.data.productList.productVariantDTO[0].attributesMap).length){
              this._addProduct.selectedVariation = res.data.productList.productVariantDTO;
             } else {
              this._addProduct.selectedVariation = [];
             }
          } else {
            this._addProduct.selectedVariation = res.data.productList.productVariantDTO;
          }
          console.log(this._addProduct.productFormGroup.value);
          for (let i = 0; i < res.data.productList.productVariantDTO.length; i++) {
            const element = res.data.productList.productVariantDTO[i];
            let temp: FormGroup = this._addProduct.initializeProductVarientDtoForEdit(element);
            this._addProduct.productVariantDTO.push(temp);
          }

          console.log('varient value',this._addProduct.productVariantDTO)
          this._addProduct.metaList = res.data.productList.productMetaInfo;
          this._addProduct.getmetaInfo();
          this._addProduct.features = JSON.parse(res.data.productList.product.features);
          console.log(this._addProduct.features);
          for (let index = 0; index < 12; index++) {
            if((res.data.productList.imageUrls.length-1) >= index){
              this._addProduct.urlArray[index] = res.data.productList.imageUrls[index];
            } else {
              this._addProduct.urlArray[index] = '-';
            }
          }
          console.log(this._addProduct.productDTO.value);
          sessionStorage.setItem(
            "addProduct",
            JSON.stringify(this._addProduct.productDTO.value)
          );
          this._router.navigateByUrl("/vendor/addProduct/vitalInfo");
        }
      });
  }

  editProductVariant(item) {
    console.log(item);
    this._router.navigate(['edit', item.product.productId, item.productVariantId])
    this._addProduct.editVarient = true;
    this._addProduct.productStatus = "EDIT";
    this._apiService.post(`product/getCompleteVariant?productVariantId=${item.productVariantId}&prodId=${item.product.productId}`).subscribe(res=>{
      if (res.isSuccess) {
        console.log(res.data.completeVariant);
        this._addProduct.selectedCatID = res.data.completeVariant.product.categoryId;
        this._addProduct.productFormGroup.patchValue(
          res.data.completeVariant.product
        );
        this._addProduct.productVariantDTO.clear();
        if(res.data.completeVariant.productVariantDTO.length == 1){
           if(Object.keys(res.data.completeVariant.productVariantDTO[0].attributesMap).length){
            this._addProduct.selectedVariation = res.data.completeVariant.productVariantDTO;
           } else {
            this._addProduct.selectedVariation = [];
           }
        } else {
          this._addProduct.selectedVariation = res.data.completeVariant.productVariantDTO;
        }
        console.log(this._addProduct.productFormGroup.value);
        for (let i = 0; i < res.data.completeVariant.productVariantDTO.length; i++) {
          const element = res.data.completeVariant.productVariantDTO[i];
          let temp: FormGroup = this._addProduct.initializeProductVarientDtoForEdit(element);
          this._addProduct.productVariantDTO.push(temp);
        }

        console.log('varient value',this._addProduct.productVariantDTO)
        this._addProduct.metaList = res.data.completeVariant.productMetaInfo;
        this._addProduct.getmetaInfo();
        this._addProduct.features = JSON.parse(res.data.completeVariant.product.features);
        console.log(this._addProduct.features);
        for (let index = 0; index < 12; index++) {
          if((res.data.completeVariant.imageUrls.length-1) >= index){
            this._addProduct.urlArray[index] = res.data.completeVariant.imageUrls[index];
          } else {
            this._addProduct.urlArray[index] = '-';
          }
        }
        console.log(this._addProduct.productDTO.value);
        sessionStorage.setItem(
          "addProduct",
          JSON.stringify(this._addProduct.productDTO.value)
        );
        this._router.navigateByUrl("/vendor/addProduct/vitalInfo");
      }
    })
  }
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
