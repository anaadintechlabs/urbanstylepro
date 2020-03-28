import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { OwlCarousel } from 'ngx-owl-carousel';
import { options } from '../../../../constants/owl-carousal';

@Component({
  selector: 'product-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.scss']
})
export class ImageSliderComponent implements OnInit {

  @Input() allImages : any[]=[];
  @ViewChild('owlElement') owlElement: OwlCarousel;
  public options = options;

  constructor() { }

  ngOnInit() {
  }

}
