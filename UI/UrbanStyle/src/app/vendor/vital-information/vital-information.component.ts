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
    private _addProductService : AddProductService,
    private _router : Router,
  ) {
    this._addProductService.changeHeaderStaus(true);
  }

  get f() {
    return this._addProductService.productFormGroup.controls;
  }

  ngOnInit() {
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
