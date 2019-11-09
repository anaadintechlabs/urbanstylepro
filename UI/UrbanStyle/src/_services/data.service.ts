import { Injectable } from '@angular/core';
import { ApiService } from './api.service'
@Injectable()
export class DataService{

    constructor(protected _apiService : ApiService){

    }

    getAllCategory(url:string,data:any){
        this._apiService.post(url,data).subscribe((res) =>{
            return JSON.parse(res);
        });
    }


}