import { Injectable } from '@angular/core';
import { ApiService } from '../http_&_login/api.service';
import { Observable } from 'rxjs';
import { ProductVariant } from 'src/_modals/productVariant';

@Injectable({
    providedIn : 'root'
})
export class DataService {

    constructor(
        private _apiService : ApiService
    ) {

    }

    getAllActiveOrInactiveProductVariantOfUser(
        url: string,
        data: any
      ): Observable<ProductVariant[]> {
        return new Observable<ProductVariant[]>(obs => {
          this._apiService.post(url, data).subscribe(res => {
            if (res.isSuccess) {
              obs.next(res.data.productList);
            }
          });
        });
      }
}