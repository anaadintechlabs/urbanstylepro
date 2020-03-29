import { Injectable } from '@angular/core';
import { Filter, FilterRequest } from 'src/_modals/filter';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn : 'root'
})
export class ProductService {

    public curruntRequest : FilterRequest = {
        searchString : "",
        catId : null,
        filterData : [],
        limit : 20,
        offset : 0
    }

    private filterSubject$ : BehaviorSubject<FilterRequest | string> = new BehaviorSubject<FilterRequest | string>(this.curruntRequest);
    readonly filter$ : Observable<FilterRequest | string> = this.filterSubject$.asObservable();
    preparedFilters : Filter[] = [];

    constructor() {
    
    }

    public applySearch() {
        if(this.curruntRequest && this.curruntRequest.searchString.length) {
            this.curruntRequest.catId = 0;
            this.curruntRequest.filterData = [];
            this.filterSubject$.next(this.curruntRequest);
        } else {
            this.filterSubject$.next('Please Input your querry.')
        }
    }

    public applyCatID(id:number) {
        this.curruntRequest.searchString = "";
        if(id) {
            this.curruntRequest.catId = id;
            this.filterSubject$.next(this.createRequestBody())
        }
    }

    public applyFilter() : void {
        this.curruntRequest.searchString = "";
        this.filterSubject$.next(this.createRequestBody());
    } 

    private createRequestBody() : FilterRequest {
        return this.curruntRequest;
    }

    
}