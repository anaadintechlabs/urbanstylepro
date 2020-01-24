
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AddProductService } from 'src/_services/product/addProductService';

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

}
