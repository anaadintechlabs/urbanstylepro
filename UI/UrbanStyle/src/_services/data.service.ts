import { Injectable } from '@angular/core';
import { ApiService } from './api.service'
import { Observable } from 'rxjs';
import { Category } from 'src/_modals/category.modal';
@Injectable()
export class DataService{

    constructor(protected _apiService : ApiService){

    }

    getAllCategory(url:string,data:any): Observable<Category[]>{
        return new Observable<Category[]>(obs =>{
            this._apiService.post(url,data).subscribe((res) =>{
                if(res.isSuccess){
                    obs.next(res.data);
                }
            });
        });
    }


}