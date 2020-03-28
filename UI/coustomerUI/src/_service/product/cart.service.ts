import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
// import { CartItem } from '../interfaces/cart-item';
import { BehaviorSubject, Observable, Subject, timer } from 'rxjs';
import { map } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';
import { ProductVerient } from 'src/_modals/product';
import { CartItem } from 'src/_modals/cartItem';
import { UserService } from '../http_&_login/user-service.service';
import { ApiService } from '../http_&_login/api.service';
import { urls } from 'src/constants/urlLists';

interface CartTotal {
    title: string;
    price: number;
    type: 'shipping'|'fee'|'tax'|'other';
}

interface CartData {
    items: CartItem[];
    quantity: number;
    subtotal: number;
    totals: CartTotal[];
    total: number;
}

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private data: CartData = {
        items: [],
        quantity: 0,
        subtotal: 0,
        totals: [],
        total: 0
    };

    private itemsSubject$: BehaviorSubject<CartItem[]> = new BehaviorSubject(this.data.items);
    private quantitySubject$: BehaviorSubject<number> = new BehaviorSubject(this.data.quantity);
    private subtotalSubject$: BehaviorSubject<number> = new BehaviorSubject(this.data.subtotal);
    private totalsSubject$: BehaviorSubject<CartTotal[]> = new BehaviorSubject(this.data.totals);
    private totalSubject$: BehaviorSubject<number> = new BehaviorSubject(this.data.total);
    private onAddingSubject$: Subject<ProductVerient> = new Subject();

    get items(): ReadonlyArray<CartItem> {
        return this.data.items;
    }

    get quantity(): number {
        return this.data.quantity;
    }

    readonly items$: Observable<CartItem[]> = this.itemsSubject$.asObservable();
    readonly quantity$: Observable<number> = this.quantitySubject$.asObservable();
    readonly subtotal$: Observable<number> = this.subtotalSubject$.asObservable();
    readonly totals$: Observable<CartTotal[]> = this.totalsSubject$.asObservable();
    readonly total$: Observable<number> = this.totalSubject$.asObservable();

    readonly onAdding$: Observable<ProductVerient> = this.onAddingSubject$.asObservable();

    constructor(
        @Inject(PLATFORM_ID)
        private platformId: any,
        private _userService : UserService,
        private _apiService : ApiService
    ) {
        if (isPlatformBrowser(this.platformId)) {
            this.load();
            this.calc();
        }
    }

    add(product: ProductVerient, quantity: number, options: {name: string; value: string}[] = []): Observable<CartItem> {
        // timer only for demo
        return timer(1000).pipe(map(() => {
            this.onAddingSubject$.next(product);

            let item = this.items.find(eachItem => {
                if (eachItem.product.productVariantId !== product.productVariantId || eachItem.options.length !== options.length) {
                    return false;
                }

                if (eachItem.options.length) {
                    for (const option of options) {
                        if (!eachItem.options.find(itemOption => itemOption.name === option.name && itemOption.value === option.value)) {
                            return false;
                        }
                    }
                }

                return true;
            });

            if (item) {
                item.quantity += quantity;
            } else {
                item = {product, quantity, options};

                this.data.items.push(item);
            }

            this.save();
            this.calc();

            return item;
        }));
    }

    update(updates: {item: CartItem, quantity: number}[]): Observable<void> {
        // timer only for demo
        return timer(1000).pipe(map(() => {
            updates.forEach(update => {
                const item = this.items.find(eachItem => eachItem === update.item);

                if (item) {
                    item.quantity = update.quantity;
                }
            });

            this.save();
            this.calc();
        }));
    }

    remove(item: CartItem): Observable<void> {
        // timer only for demo
        return timer(1000).pipe(map(() => {
            this.data.items = this.data.items.filter(eachItem => eachItem !== item);

            this.save();
            this.calc();
        }));
    }

    private calc(): void {
        let quantity = 0;
        let subtotal = 0;

        this.data.items.forEach(item => {
            quantity += item.quantity;
            subtotal += item.product.displayPrice * item.quantity;
        });

        const totals: CartTotal[] = [];

        totals.push({
            title: 'Shipping',
            price: 25,
            type: 'shipping'
        });
        totals.push({
            title: 'Tax',
            price: subtotal * 0.20,
            type: 'tax'
        });

        const total = subtotal + totals.reduce((acc, eachTotal) => acc + eachTotal.price, 0);

        this.data.quantity = quantity;
        this.data.subtotal = subtotal;
        this.data.totals = totals;
        this.data.total = total;

        this.itemsSubject$.next(this.data.items);
        this.quantitySubject$.next(this.data.quantity);
        this.subtotalSubject$.next(this.data.subtotal);
        this.totalsSubject$.next(this.data.totals);
        this.totalSubject$.next(this.data.total);
    }

    private save(): void {
        if(this._userService.getCurrentUser()) {
            if(Object.keys(this._userService.getCurrentUser()).length) {
                localStorage.setItem('cartItems', JSON.stringify(this.data.items));
                this.saveCart();
            } else {
                localStorage.setItem('cartItems', JSON.stringify(this.data.items));
            }
        } else {
            localStorage.setItem('cartItems', JSON.stringify(this.data.items));
        }
    }

    private saveCart() {
        this._apiService.post(urls.saveCart,this.allData()).subscribe(res=>{
            console.log(res);
        })
    }


    private allData() : any {
        let currunt_user = JSON.parse(this._userService.getUser());
        let obj = {
            user : {id:currunt_user.id},
            shoppingCartItemDTO : []
        }
        this.data.items.forEach(ele=>{
            obj.shoppingCartItemDTO.push({productVariant : {productVariantId : ele.product.productVariantId},quantity:ele.quantity})
        });
        console.log(obj);
        return obj;
    }

    private load(): void {
        const items = localStorage.getItem('cartItems');

        if (items) {
            this.data.items = JSON.parse(items);
        }
    }
}
