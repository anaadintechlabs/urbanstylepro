import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/_service/product/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'topBarSearch',
  templateUrl: './top-bar-search.component.html',
  styleUrls: ['./top-bar-search.component.scss']
})
export class TopBarSearchComponent implements OnInit {

  constructor(
    public _productService : ProductService,
    private _router : Router
  ) { }

  ngOnInit(): void {
  }

  submit() {
    this._productService.applySearch();
    this._router.navigateByUrl('/classic/shop');
  }

}
