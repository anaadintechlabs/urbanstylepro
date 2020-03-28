import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { HttpParams } from '@angular/common/http';
import { ProductVerient } from 'src/_modals/product';

type SingleProduct = {
  mainProductPacket : Productpacket;
  relatedProductsPackets : Productpacket[]; 
  allReviews : any[]
}

type Productpacket = {
  mainProduct : MainProduct;
  allImages : any[];
}

type MainProduct = {
  productVariant : ProductVerient;
  attributesMap : any;
}

@Component({
  selector: 'app-single-product-view',
  templateUrl: './single-product-view.component.html',
  styleUrls: ['./single-product-view.component.scss']
})
export class SingleProductViewComponent implements OnInit {
  
  public singleProductData: SingleProduct;
  public avgRating : string;

  constructor(
    private route : ActivatedRoute,
    private _apiservice :ApiService
  ) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params=>{
      this.getProductData(params.get('id'))
    })
  }

  getProductData(id:string){
    this._apiservice.get(urls.singleProduct,new HttpParams().set('prodVarId',id)).subscribe(res=>{
      if(res.isSuccess){
        this.singleProductData = res.data.SingleProductDetail;
      }
    });
    this._apiservice.get(urls.avrgRatingProduct,new HttpParams().set('productId',id)).subscribe(res=>{
      if(res.isSuccess){
        this.avgRating = res.data.averageRating.toString();
        // console.log("avg rating",res);
      }
    })
  }

  scroll(el : HTMLElement) {
    el.scrollIntoView({behavior:"smooth", inline  : 'nearest', block : 'nearest'});
  }

}
