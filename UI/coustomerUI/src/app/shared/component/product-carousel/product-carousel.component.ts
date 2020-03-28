import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ProductVerient } from 'src/_modals/product';
import { OwlCarousel } from 'ngx-owl-carousel';
import { options } from '../../../../constants/owl-carousal';

@Component({
  selector: 'product-carousel',
  templateUrl: './product-carousel.component.html',
  styleUrls: ['./product-carousel.component.scss']
})
export class ProductCarouselComponent implements OnInit {

  @Input() productList : ProductVerient[];
  @ViewChild('owlElement') owlElement: OwlCarousel;
  public options = options;
  
  constructor() { }

  ngOnInit() {
  }

}
