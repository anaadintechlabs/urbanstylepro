import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
    selector: 'vendor-home',
    templateUrl: './vendor.component.html',
    styleUrls: ['./vendor.component.scss']
  })
export class VendorComponent implements OnInit {
    header_status : boolean;
    
    constructor(
      private _addProduct : AddProductService
    ){
      this._addProduct.headerStatus$.subscribe(data=>{
        console.log(data);
        this.header_status = data;
      })
    }

    ngOnInit(){

    }
}