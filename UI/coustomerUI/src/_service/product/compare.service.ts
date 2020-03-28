import { Inject, Injectable, OnDestroy, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject, Observable, Subject, timer } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';
import { ProductVerient } from 'src/_modals/product';

interface CompareData {
    items: ProductVerient[];
}

@Injectable({
    providedIn: 'root'
})
export class CompareService implements OnDestroy {
    private data: CompareData = {
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
        private platformId: any
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
        localStorage.setItem('compareItems', JSON.stringify(this.data.items));

        this.itemsSubject$.next(this.data.items);
    }

    private load(): void {
        const items = localStorage.getItem('compareItems');

        if (items) {
            this.data.items = JSON.parse(items);
            this.itemsSubject$.next(this.data.items);
        }
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
