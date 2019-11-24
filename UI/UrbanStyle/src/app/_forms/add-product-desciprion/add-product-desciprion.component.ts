import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CategoryAttribute } from 'src/_modals/categoryAttribute.modal';

@Component({
  selector: 'product-desciprion',
  templateUrl: './add-product-desciprion.component.html',
  styleUrls: ['./add-product-desciprion.component.scss']
})
export class AddProductDesciprionComponent implements OnInit {

  @Output() productType : EventEmitter<string> = new EventEmitter<string>();
  @Input() categoryAttribute : CategoryAttribute[];

  selectedProductType : string;
  selectedVariation : CategoryAttribute[]=[];
  constructor() { }

  ngOnInit() {
    console.log(this.categoryAttribute);
  }

  onChange(value : string) : void {
    this.selectedProductType = value;
    this.productType.emit(this.selectedProductType);
  }

  addvariation(event, data:CategoryAttribute) {
    console.log(event);
    if(event.checked){

    } else {

    }
  }

}
