import { Component, OnInit } from '@angular/core';
import { WishlistService } from 'src/_service/product/wishlist.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  constructor(
    public _wishList : WishlistService
  ) { }

  ngOnInit() {
    this._wishList.items$.subscribe(data=>{
      console.log(data);
    })
  }

}
