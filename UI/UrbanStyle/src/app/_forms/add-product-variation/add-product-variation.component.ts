import { Component, OnInit, Output, Input, EventEmitter } from "@angular/core";
import { FormGroup } from "@angular/forms/forms";

@Component({
  selector: 'product-variation',
  templateUrl: './add-product-variation.component.html',
  styleUrls: ['./add-product-variation.component.scss']
})
export class AddProductVariationComponent implements OnInit {

  @Output() productDetails : EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
  @Input() productVariation : FormGroup;

  constructor() { }

  ngOnInit() {
  }

}
