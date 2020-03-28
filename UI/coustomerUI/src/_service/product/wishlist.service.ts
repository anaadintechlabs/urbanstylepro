import { Inject, Injectable, OnDestroy, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject, Observable, Subject, timer } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';
import { ProductVerient } from 'src/_modals/product';
import { ApiService } from '../http_&_login/api.service';
import { UserService } from '../http_&_login/user-service.service';
import { urls } from 'src/constants/urlLists';
import { HttpParams } from '@angular/common/http';

interface WishlistData {
    items: ProductVerient[];
}

@Injectable({
    providedIn: 'root'
})
export class WishlistService implements OnDestroy {
    private data: WishlistData = {
        items: []
    };

    private destroy$: Subject<void> = new Subject();
    private itemsSubject$: BehaviorSubject<ProductVerient[]> = new BehaviorSubject([]);
    private onAddingSubject$: Subject<ProductVerient> = new Subject();

    readonly items$: Observable<ProductVerient[]> = this.itemsSubject$.pipe(takeUntil(this.destroy$));
    readonly count$: Observable<number> = this.itemsSubject$.pipe(map(items => items.length));
    readonly onAdding$: Observable<ProductVerient> = this.onAddingSubject$.asObservable();

    constructor(
        @Inject(PLATFORM_ID)
        private platformId: any,
        private _apiService : ApiService,
        private _userService : UserService
    ) {
        if (isPlatformBrowser(this.platformId)) {
            this.load();
        }
    }

    add(product: ProductVerient): Observable<void> {
        // timer only for demo
        return timer(1000).pipe(map(() => {
            this.onAddingSubject$.next(product);

            const index = this.data.items.findIndex(item => item.productVariantId === product.productVariantId);

            if (index === -1) {
                this.data.items.push(product);
                this.save();
            }
        }));
    }

    remove(product: ProductVerient): Observable<void> {
        // timer only for demo
        return timer(1000).pipe(map(() => {
            const index = this.data.items.findIndex(item => item.productVariantId === product.productVariantId);

            if (index !== -1) {
                this.data.items.splice(index, 1);
                this.save();
            }
        }));
    }

    private save(): void {
        localStorage.setItem('wishlistItems', JSON.stringify(this.data.items));
        this.itemsSubject$.next(this.data.items);
        this.saveCart();
    }
    
    private saveCart() {
        this._apiService.post(urls.saveWishList,this.allData()).subscribe(res=>{
            console.log(res);
        })
    }

    private allData() : any {
        let currunt_user = JSON.parse(this._userService.getUser());
        let obj = {
            user : {id:currunt_user.id},
            productVariant : []
        }
        this.data.items.forEach(ele=>{
            obj.productVariant.push({productVariantId : ele.productVariantId})
        });
        console.log(obj);
        return obj;
    }
    
    private load(): void {
        const items = localStorage.getItem('wishlistItems');

        if (items) {
            this.data.items = JSON.parse(items);
            this.itemsSubject$.next(this.data.items);
        }
    }

    getWishListOfUser(id:number) {
        console.log(id);
        let param : HttpParams = new HttpParams();
        param.set('userId',id.toString());
        let body = {
          "limit":15,
          "offset":0,
          "sortingDirection":"asc",
          "sortingField":"createdDate"
        }
        this._apiService.post(`${urls.wishList}?userId=${id}`,body).subscribe( data=> {
          console.log(data);
          if(data.data.wishList.length){
            localStorage.removeItem('wishlistItems');
            localStorage.setItem('wishlistItems', data.data.wishList);
          }
        })
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
