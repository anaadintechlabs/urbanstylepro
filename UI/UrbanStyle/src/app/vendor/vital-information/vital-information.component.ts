import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
// import { AddProductService } from '../Services/addProduct.service';
import { Router } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';
// import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: 'vital-information',
  templateUrl: './vital-information.component.html',
  styleUrls: ['./vital-information.component.scss']
})
export class VitalInformationComponent implements OnInit {

  public submitted:false;
  constructor(
    public _addProductService : AddProductService,
    private _router : Router,
  ) {
    this._addProductService.changeHeaderStaus(true);
    
    if(this._addProductService.selectedVariation.length == 0){
      this._addProductService.productVariantDTO.push(this._addProductService.initializeProductVarientDto());
     } 
  }

  get f() {
    return this._addProductService.productFormGroup.controls;
  }

  ngOnInit() {
    console.log("selected variation", this._addProductService.selectedVariation);
    console.log("form here is",this._addProductService.productFormGroup.controls)
  }

  onSubmit() {
     console.log("form here on is",this._addProductService.productFormGroup.controls)
    this._router.navigateByUrl('/vendor/addProduct/prodDesc');
  }
  cancelButton() {
    this.submitted=false;
    this._router.navigateByUrl('/vendor/addProduct/categorySelection');
  }
}
