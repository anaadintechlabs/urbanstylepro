import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { ProductService } from 'src/_service/product/product.service';
import { FilterRequest } from 'src/_modals/filter';


@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {

  allData : any;

  constructor(
    private _apiService : ApiService,
    public  _productService : ProductService,
  ) { 
    this._productService.filter$.subscribe(data=>{
      console.log(data);
      this.getAllProduct(data as FilterRequest);
    })
  }

  ngOnInit(): void {
    // this.getAllProduct(this._productService.curruntRequest);
  }

  getAllProduct(data:FilterRequest) {
    let body = {
      filterData : data.filterData,
      catId : data.catId
    }
    this._apiService.post(urls.filter+'?searchString='+data.searchString+'&catId='+data.catId).subscribe(res=>{
      console.log(res);
      this.allData = res.data.HomePageFilter;
    })
  }

}
