import { Component, OnInit, ViewChild } from '@angular/core';
import { OwlCarousel } from 'ngx-owl-carousel';

@Component({
  selector: 'brand-slider',
  templateUrl: './brand-slider.component.html',
  styleUrls: ['./brand-slider.component.scss']
})
export class BrandSliderComponent implements OnInit {

  @ViewChild('owlElement') owlElement: OwlCarousel;

  public options = {
    items: 5, 
    dots: false, 
    navigation: false,
    loop : false,
    nav: false,
    margin : 20,
    autoplay : true,
  }

  constructor() { }

  ngOnInit(): void {
  }

  imgs = [
    'assets/img/brand/001.png',
    'assets/img/brand/002.png',
    'assets/img/brand/003.png',
    'assets/img/brand/004.png',
    'assets/img/brand/005.png',
    'assets/img/brand/006.png',
  ]

}
