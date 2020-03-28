import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ProductVerient } from 'src/_modals/product';
import { OwlCarousel } from 'ngx-owl-carousel';
import { options } from '../../../../constants/owl-carousal';

@Component({
  selector: 'app-carousal2',
  templateUrl: './carousal2.component.html',
  styleUrls: ['./carousal2.component.scss']
})
export class Carousal2Component implements OnInit {

  @Input() productList : any[];
  @ViewChild('owlElement') owlElement: OwlCarousel;
  public options = options;
  
  constructor() { }

  ngOnInit() {
  }

}


