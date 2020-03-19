import { Component, OnInit } from '@angular/core';
import { AddProductService } from 'src/_services/product/addProductService';
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-meta-info',
  templateUrl: './meta-info.component.html',
  styleUrls: ['./meta-info.component.scss']
})
export class MetaInfoComponent implements OnInit {

  constructor(
    public _addProduct : AddProductService,
    private _router:Router,
     
  ) { 
    // this._addProduct.getmetaInfo();
    console.log("metalist",this._addProduct.getProductMetaAllInfo);
    console.log("meta list in meta info", this._addProduct.metaList);
    this._addProduct.metaList.forEach(element => {
      if(element.unitsAvailable && typeof(element.dropDownValues)=='string') {
        element.dropDownValues = element.dropDownValues.split(',');
        element['selectedDropDown'] = ""
      } else {

      }
      if(element.subKeyAvailable && typeof(element.subKeys)=='string') {
        element.subKeys = element.subKeys.split(',');
      } else {

      }
    });
  }

  ngOnInit() {
  }

  onSubmit(){
    console.log(this._addProduct.productDTO);
    this._addProduct.saveChanges();
  }

  cancelButton(){
    console.log(this._addProduct.getProductMetaAllInfo)
    this._router.navigateByUrl('/vendor/addProduct/imageUpload');
  }
}
