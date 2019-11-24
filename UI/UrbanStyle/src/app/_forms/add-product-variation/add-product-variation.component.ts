import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'product-variation',
  templateUrl: './add-product-variation.component.html',
  styleUrls: ['./add-product-variation.component.scss']
})
export class AddProductVariationComponent implements OnInit {
  
  @Input() productVariation : FormGroup;
  constructor() { }

  ngOnInit() {
    console.log(this.productVariation);
  }

}
