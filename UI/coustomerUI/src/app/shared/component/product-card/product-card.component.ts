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

@Component({
  selector: "product-card",
  templateUrl: "./product-card.component.html",
  styleUrls: ["./product-card.component.scss"]
})
export class ProductCardComponent implements OnInit {
  private destroy$: Subject<void> = new Subject();

  @Input() product: ProductVerient;

  addingToCart = false;
  addingToWishlist = false;
  addingToCompare = false;
  showingQuickview = false;

  constructor(
    private cd: ChangeDetectorRef,
    public cart: CartService,
    public wishlist: WishlistService,
    public compare: CompareService,
    public currency : CurrencyService
  ) {}

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
        this.addingToWishlist = false;
        this.cd.markForCheck();
      }
    });
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
    let url : string = `${location.origin}/classic/shop/product/${this.product.productVariantId}`
    window.open(url,'_blank');
  }

  // showQuickview(): void {
  //   if (this.showingQuickview) {
  //     return;
  //   }

  //   this.showingQuickview = true;
  //   this.quickview.show(this.product).subscribe({
  //     complete: () => {
  //       this.showingQuickview = false;
  //       this.cd.markForCheck();
  //     }
  //   });
  // }
}
