import { Injectable } from "@angular/core";
import { ApiService } from "../http_&_login/api.service";
import { Observable } from "rxjs";
import { Category } from "src/_modals/category.modal";
import { CategoryAttribute } from "src/_modals/categoryAttribute.modal";
import {
  HttpParams,
  HttpBackend,
  HttpHeaders,
  HttpClient
} from "@angular/common/http";
@Injectable()
export class DataService {
  constructor(protected _apiService: ApiService,private httpBackend:HttpBackend) {}

  getAllCategory(url: string, data: any): Observable<Category[]> {
    return new Observable<Category[]>(obs => {
      this._apiService.post(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.categoryList);
        }
      });
    });
  }

  getAllSubCategoriesOfCategory(
    url: string,
    categoryId: any
  ): Observable<Category[]> {
    const param: HttpParams = new HttpParams().set("categoryId", categoryId);
    console.log(param);
    return new Observable<Category[]>(obs => {
      this._apiService.get(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.categoryList);
        }
      });
    });
  }

  //Method for getting all variations of category
  getAllVariationOfCategory(
    url: string,
    categoryId: any
  ): Observable<CategoryAttribute[]> {
    const param: HttpParams = new HttpParams().set("categoryId", categoryId);
    console.log(param);
    return new Observable<CategoryAttribute[]>(obs => {
      this._apiService.get(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.variationList);
        }
      });
    });
  }

  saveProduct(url, data): Observable<CategoryAttribute[]> {
   

    return new Observable<any[]>(obs => {
      this._apiService.postWithMedia(url, data).subscribe(res => {
        console.log("product saved", res);
      });
    });
  }
}
