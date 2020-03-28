import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit
} from "@angular/core";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { CartService } from "src/_service/product/cart.service";
import { WishlistService } from "src/_service/product/wishlist.service";
import { CompareService } from "src/_service/product/compare.service";
import { ProductVerient } from 'src/_modals/product';
import { CurrencyService } from '../../services/currency.service';
import { UserService } from 'src/_service/http_&_login/user-service.service';

@Component({
  selector: 'productCard2',
  templateUrl: './product-card2.component.html',
  styleUrls: ['./product-card2.component.scss']
})
export class ProductCard2Component implements OnInit {

  private destroy$: Subject<void> = new Subject();

  @Input() product: ProductVerient;

  addingToCart = false;
  addingToWishlist = false;
  addingToCompare = false;
  showingQuickview = false;
  isLoggedIn : boolean;

  constructor(
    private cd: ChangeDetectorRef,
    public cart: CartService,
    public wishlist: WishlistService,
    public compare: CompareService,
    public currency : CurrencyService,
    public _userService : UserService
  ) {
    setTimeout(() => {
      let allWishListItem : ProductVerient[] = [];
      if(localStorage.getItem('wishlistItems') && localStorage.getItem('wishlistItems').length) {
        allWishListItem = JSON.parse(localStorage.getItem('wishlistItems'));
        allWishListItem.forEach(element => {
          if(this.product.productVariantId == element.productVariantId) {
            this.addingToWishlist = true;
          }
        });
      }
    }, 300);
  }

  ngOnInit(): void {
    this.currency.changes$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.cd.markForCheck();
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  addToCart(): void {
    if (this.addingToCart) {
      return;
    }

    this.addingToCart = true;
    this.cart.add(this.product, 1).subscribe({
      complete: () => {
        // this.addingToCart = false;
        this.cd.markForCheck();
      }
    });
  }

  addToWishlist(): void {
    if (this.addingToWishlist) {
     this.wishlist.remove(this.product).subscribe({
       complete: () =>{
         this.cd.markForCheck();
       }
     })
    }

    this.addingToWishlist = true;
    this.wishlist.add(this.product).subscribe({
      complete: () => {
        // this.addingToWishlist = false;
        this.cd.markForCheck();
      }
    });
  }

  removeFromWishList() {
    this.wishlist.remove(this.product).subscribe({
      complete: () => {
        this.addingToWishlist = false;
        this.cd.markForCheck();
      }
    })
  }

  addToCompare(): void {
    if (this.addingToCompare) {
      return;
    }

    this.addingToCompare = true;
    this.compare.add(this.product).subscribe({
      complete: () => {
        this.addingToCompare = false;
        this.cd.markForCheck();
      }
    });
  }

  openPage() {
    console.log(location.origin);
    let url : string = `${location.origin}/classic/shop/product/${this.product.uniqueprodvarId}`
    window.open(url,'_blank');
  }

}
