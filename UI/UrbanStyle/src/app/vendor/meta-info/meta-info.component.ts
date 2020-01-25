import { Component, OnInit } from '@angular/core';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: 'app-meta-info',
  templateUrl: './meta-info.component.html',
  styleUrls: ['./meta-info.component.scss']
})
export class MetaInfoComponent implements OnInit {

  constructor(
    private _addProduct : AddProductService
  ) { 
    this._addProduct.getmetaInfo();
    console.log("metalist",this._addProduct.getProductMetaAllInfo);
  }

  ngOnInit() {
  }

  onSubmit(){
    console.log(this._addProduct.productDTO);
    this._addProduct.saveChanges();
  }

}
