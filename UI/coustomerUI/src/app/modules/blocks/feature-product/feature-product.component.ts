import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { OwlCarousel } from 'ngx-owl-carousel';
import { options } from '../../../../constants/owl-carousal';
import { ProductVerient } from 'src/_modals/product';

@Component({
  selector: 'block-feature-product',
  templateUrl: './feature-product.component.html',
  styleUrls: ['./feature-product.component.scss']
})
export class BlockFeatureProductComponent implements OnInit {
  
  @ViewChild('owlElement') owlElement: OwlCarousel;
  public options = options;

  @Input() fratureProductList : ProductVerient[];
  @Input() title : string;

  constructor() { }

  ngOnInit() {
  }



}
