import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';

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
  
  constructor() { }
  
  ngOnInit() {
  }
  
  get f() { return this.productVariation.controls.productVariant;}
  
  onSubmit(){
    this.submitted = true;
    if(this.productVariation.invalid){
    } else {
      console.log(this.productVariation);
      this.productVariationEmit.emit(this.productVariation);
    }
  }
}
