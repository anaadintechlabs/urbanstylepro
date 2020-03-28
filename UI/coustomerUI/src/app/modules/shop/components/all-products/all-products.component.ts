import { Component, OnInit, Input } from '@angular/core';
import { ProductVerient } from 'src/_modals/product';

@Component({
  selector: 'shop-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.scss']
})
export class AllProductsComponent implements OnInit {

  @Input() productList : ProductVerient[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
