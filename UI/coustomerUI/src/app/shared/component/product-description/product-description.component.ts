import { Component, OnInit, Input, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { ProductVerient } from 'src/_modals/product';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { CartService } from 'src/_service/product/cart.service';
import { WishlistService } from 'src/_service/product/wishlist.service';
import { CurrencyService } from '../../services/currency.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { CartItem } from 'src/_modals/cartItem';
import { OrderService } from 'src/_service/product/order.service';


type SingleProduct = {
  mainProductPacket : Productpacket;
  relatedProductsPackets : Productpacket[]; 
  allReviews : any[]
}

type Productpacket = {
  mainProduct : MainProduct;
  allImages : any[];
}

type MainProduct = {
  productVariant : ProductVerient;
  attributesMap : any;
}

@Component({
  selector: 'product-description',
  templateUrl: './product-description.component.html',
  styleUrls: ['./product-description.component.scss'],
})
export class ProductDescriptionComponent implements OnInit {
  private destroy$: Subject<void> = new Subject();

  @Input() description : any;
  @Input() avrageRating : string;
  @Output() scrollToForm : EventEmitter<void> = new EventEmitter<void>();

  addingToCart : boolean = false;
  addingToWishlist : boolean = false;
  selectedQuantity : number = 1;
  productID : string = "";

  constructor(
    public _userService : UserService,
    private cd: ChangeDetectorRef,
    private _apiService : ApiService,
    private _cart : CartService,
    private _wishlist : WishlistService,
    private currency : CurrencyService,
    private _route : ActivatedRoute,
    private _router : Router,
    private _orderService : OrderService,
  ) { 
    this._route.paramMap.subscribe(param=>{
      this.productID = param.get('id');
    })
  }

  ngOnInit() {
    this.currency.changes$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.cd.markForCheck();
    });

    this._cart.items$.subscribe(data=> {
      data.forEach(ele=>{
        if(ele.product.productVariantId.toString() == this.productID) {
          this.addingToCart = true;
        }
      });
    });

    this._wishlist.items$.subscribe(data=>{
      data.forEach(ele=>{
        if(ele.productVariantId.toString() == this.productID) {
          this.addingToWishlist = true;
        }
      });
    })

    this.description.variants.forEach(element => {
      element.variationData.forEach(ele => {
        ele['selected'] = false;
      });
    });
  }

  cartAdd() {
    this.selectedQuantity++;
  }

  changeVarient(data,item) {
    item.variationData.forEach(element => {
      if(element == data) {
        element.selected = true;
      } else {
        element.selected = false;
      }
    });
    this.makeSelectedCombination();
  }

  makeSelectedCombination(){
    let selectedData : any = [];
    this.description.variants.forEach(element => {
      element.variationData.forEach(ele => {
        if(ele.selected){
          selectedData.push(ele);
        }
      });
    });
    console.log(selectedData);
    this.findSelectedCombination(selectedData)
  }

  findSelectedCombination(data) {
    let varientExist : boolean = false;
    let selectedVarient : any;
    this.description.variantCombinations.forEach(element => {
      element.variationData.forEach(ele => {
          ele.selected = true;
      });
    });
    this.description.variantCombinations.forEach(element => {
      if(element.variationData.toString() == data.toString()) {
        varientExist = true;
        selectedVarient = element;
      }
    });
    console.log(varientExist);
    console.log(selectedVarient);
  }

  cartMinus() {
    if(this.selectedQuantity > 1){
      this.selectedQuantity--;
    }
  }

  addToCart(): void {
    if (this.addingToCart) {
      return;
    }
    this.addingToCart = true;
    this._cart.add(this.description.mainProductPacket.mainProduct.productVariant, this.selectedQuantity).subscribe({
      complete: () => {
        // this.addingToCart = false;
        this.cd.markForCheck();
      }
    });
  }

  scroll() {
    this.scrollToForm.emit();
  } 


  update(): void {
    // this.updating = true;
    this._cart.update(
        this._cart.items
            .filter(item => item.product.productVariantId.toString() == this.productID)
            .map(data => ({
                item: data,
                quantity: this.selectedQuantity
            }))
    ).subscribe({
      complete: () => console.log("data")
    });
}

  addToWishlist(): void {
    if (this.addingToWishlist) {
     this._wishlist.remove(this.description.mainProductPacket.mainProduct.productVariant).subscribe({
       complete: () =>{
         this.cd.markForCheck();
       }
     })
    }

    this.addingToWishlist = true;
    this._wishlist.add(this.description.mainProductPacket.mainProduct.productVariant).subscribe({
      complete: () => {
        // this.addingToWishlist = false;
        this.cd.markForCheck();
      }
    });
  }

  removeFromWishList() {
    this._wishlist.remove(this.description.mainProductPacket.mainProduct.productVariant).subscribe({
      complete: () => {
        this.addingToWishlist = false;
        this.cd.markForCheck();
      }
    })
  }

  checkout(data:ProductVerient) {
    this.prepareCheckoutItem(data);
    this._router.navigate(['/classic/order/']);
  }

  prepareCheckoutItem(data:ProductVerient){
    console.log(data);
    let cartItem : CartItem = {
      options : [],
      product : data,
      quantity : 1
    }
    this._orderService.SinglecheckoutItem = cartItem;
    console.log(this._orderService.SinglecheckoutItem);
  }
}
