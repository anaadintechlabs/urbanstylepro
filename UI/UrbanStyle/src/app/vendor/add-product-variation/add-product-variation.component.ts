
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AddProductService } from 'src/_services/product/addProductService';
import { element } from 'protractor';

@Component({
  selector: 'product-variation',
  templateUrl: './add-product-variation.component.html',
  styleUrls: ['./add-product-variation.component.scss']
})
export class AddProductVariationComponent implements OnInit {

  
  @Output() productVariationEmit : EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
  @Input() productVariation : FormGroup;
  @Input() index : number;
  
  submitted : boolean = false;  

  productVariantGroup:FormGroup;
  
  constructor(
    private _addProduct : AddProductService
  ) {
    console.log("jaduuuu",this._addProduct.productFormGroup);
   }
  
  ngOnInit() {
     console.log("in construcotr",this.productVariation);
  }
  
  get f() { return this.productVariation.controls.productVariant;}
  
  onSubmit(){
    this.submitted = true;
    console.log("obsubmit",this.productVariation);
    if(this.productVariation.invalid){
    } else {
      console.log(this.productVariation);
      this.productVariationEmit.emit(this.productVariation);
    }
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
    console.log(this._addProduct.productForm.value.productName);
    formGroup.controls.variantName.patchValue(`${this._addProduct.productForm.value.productName ? this._addProduct.productForm.value.productName : ""} (${temp.join('-')})`);
    return `${this._addProduct.productForm.value.productName ? this._addProduct.productForm.value.productName : ""} (${temp.join('-')})`;
  }

  saleCheck:boolean = false;

  changesaleCheck(){
    // this.saleCheck = !this.saleCheck
  }

}
