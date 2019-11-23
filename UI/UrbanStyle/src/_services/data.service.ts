import { Injectable } from '@angular/core';
import { ApiService } from './http_&_login/api.service'
import { Observable } from 'rxjs';
import { Category } from 'src/_modals/category.modal';
import { HttpParams } from "@angular/common/http";
@Injectable()
export class DataService{

    constructor(protected _apiService : ApiService){

    }

    getAllCategory(url:string,data:any): Observable<Category[]>{
        return new Observable<Category[]>(obs =>{
            this._apiService.post(url,data).subscribe((res) =>{
                if(res.isSuccess){
                    obs.next(res.data.categoryList);
                }
            });
        });
    }

      getAllSubCategoriesOfCategory(url:string,categoryId:any): Observable<Category[]>{
        const param : HttpParams = new HttpParams()
            .set('categoryId' , categoryId);
        console.log(param);
        return new Observable<Category[]>(obs =>{
            this._apiService.get(url,param).subscribe((res) =>{
                if(res.isSuccess){
                    obs.next(res.data.categoryList);
                }
            });
        });
    }

    //Method for getting all variations of category
    getAllVariationOfCategory(url:string,categoryId:any): Observable<Category[]>{
        const param : HttpParams = new HttpParams()
            .set('categoryId' , categoryId);
        console.log(param);
        return new Observable<Category[]>(obs =>{
            this._apiService.get(url,param).subscribe((res) =>{
                if(res.isSuccess){
                    obs.next(res.data.variationList);
                }
            });
        });
    }
    


}