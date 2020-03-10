import { Component, OnInit } from '@angular/core';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: 'app-extra-details',
  templateUrl: './extra-details.component.html',
  styleUrls: ['./extra-details.component.scss']
})
export class ExtraDetailsComponent implements OnInit {

  constructor(
    public _addProductService : AddProductService
  ) { }

  ngOnInit() {
    console.log(this._addProductService.productDTO.value);
  }

  get f() {
    return this._addProductService.productDescFormGroup.controls;
  }

  addMoreFeature() {
    
  }

}
