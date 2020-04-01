import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray } from '@angular/forms';
import { EditProductService } from '../../service/editProduct.service';

@Component({
  selector: 'app-variants',
  templateUrl: './variants.component.html',
  styleUrls: ['./variants.component.scss']
})
export class VariantsComponent implements OnInit {

  constructor(
    public _editProduct : EditProductService
  ) { }

  ngOnInit() {
    // this.initializeAllData();
  }

  getVarient(item : FormGroup) {
    let temp : string[]=[]; 
    let data = item.value.attributesMap;
    let formGroup : FormGroup = item.controls.productVariant as FormGroup;
    var result = Object.keys(data).map(function (key) {    
      // Using Number() to convert key to number type 
      // Using obj[key] to retrieve key value 
      return [data[key]]; 
    });
    result.forEach(ele=>{
      temp.push(ele[0])
    });
    // console.log(this._editProduct.productForm.value.productName);
    formGroup.controls.variantName.patchValue(`${this._editProduct.productForm.value.productName ? this._editProduct.productForm.value.productName : ""} (${temp.join('-')})`);
    return `${this._editProduct.productForm.value.productName ? this._editProduct.productForm.value.productName : ""} (${temp.join('-')})`;
  }

}
