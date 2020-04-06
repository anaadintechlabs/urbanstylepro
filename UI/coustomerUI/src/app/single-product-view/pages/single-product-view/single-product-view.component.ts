import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-single-product-view',
  templateUrl: './single-product-view.component.html',
  styleUrls: ['./single-product-view.component.scss']
})
export class SingleProductViewComponent implements OnInit {
  public productId : any;
  public singleProductData : any;
  public avgRating : any;

  constructor(
    private router : ActivatedRoute,
    public _apiservice : ApiService
  ) { 
    this.router.params.subscribe(data=>{
      console.log(data);
      this.productId = data.id;
      console.log(this.productId);
      this.getProductData();
    })
  }

  ngOnInit(): void {
  }

  getProductData(){
    const param = new HttpParams()
    .set('prodVarId', this.productId);
    this._apiservice.get(urls.singleProduct,param).subscribe(res=>{
      if(res.isSuccess){
        this.singleProductData = res.data.SingleProductDetail;
      }
    });
    const param2 = new HttpParams()
    .set('productId', this.productId);
    this._apiservice.get(urls.avrgRatingProduct,param2).subscribe(res=>{
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
