import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EditProductService } from './service/editProduct.service';
import { ApiService } from 'src/_services/http_&_login/api.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {

  constructor(
    private _router : ActivatedRoute,
    private _editProduct : EditProductService,
    private _apiService : ApiService
  ) {
    this._router.params.subscribe(data=>{
      console.log(data);
      this._editProduct.produtId = data.productID;
      this._editProduct.variantId = data.variantID;
      if(data.variantID == '0'){
        this.getProduct();
      } else {
        this.getProductVarients();
      }
    })
   }

  ngOnInit() {
  }

  getProduct() {
    this._apiService
    .post(`product/getCompleteProduct?prodId=${this._editProduct.produtId}`)
    .subscribe(res => {
      if (res.isSuccess) {
        console.log(res.data.completeVariant);
        this._editProduct.productData = res.data.productList;
        this._editProduct.initializeData();
      }
    })
  }

  getProductVarients() {
    this._apiService.post(`product/getCompleteVariant?productVariantId=${this._editProduct.variantId}&prodId=${this._editProduct.produtId}`).subscribe(res=>{
      if (res.isSuccess) {
        console.log(res.data.completeVariant);
        this._editProduct.productData = res.data.completeVariant;
        this._editProduct.initializeData();
      }
    });
  }

}
