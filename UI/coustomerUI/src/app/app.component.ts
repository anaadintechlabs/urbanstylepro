import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ViewportScroller } from '@angular/common';
import { CurrencyService } from './shared/services/currency.service';
import { CartService } from 'src/_service/product/cart.service';
import { WishlistService } from 'src/_service/product/wishlist.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  title = 'urbanStyleCustomer';
  constructor(
    private router: Router,
    private toastr: ToastrService,
    private cart: CartService,
    // private compare: CompareService,
    private wishlist: WishlistService,
    // private zone: NgZone,
    private scroller: ViewportScroller,
    private currency: CurrencyService
  ) {
    this.router.events.subscribe((event) => {
      if ((event instanceof NavigationEnd)) {
          this.scroller.scrollToPosition([0, 0]);
      }
    });

    this.cart.onAdding$.subscribe(product => {
      this.toastr.success(`Product "${product.product.productName}" added to cart!`);
    });
    // this.compare.onAdding$.subscribe(product => {
    //     this.toastr.success(`Product "${product.name}" added to compare!`);
    // });
    this.wishlist.onAdding$.subscribe(product => {
        this.toastr.success(`Product "${product.product.productName}" added to wish list!`);
    });
  }

  ngOnInit(): void {
    this.currency.options = {
      code: '₹',
      display: 'symbol',
      // digitsInfo: '1.2-2',
      locale: 'en-IND'
  };
  }
}
