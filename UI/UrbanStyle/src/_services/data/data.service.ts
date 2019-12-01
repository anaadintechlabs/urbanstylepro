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
import { Address } from "src/_modals/address";
import { Country, State, City } from "src/_modals/country";
import { BankDetails } from "src/_modals/bankdetails";
import { WishList } from "src/_modals/wishlist";
import { ProductVariant } from "src/_modals/productVariant";
import { Product } from "src/_modals/product";
@Injectable()
export class DataService {
  constructor(
    protected _apiService: ApiService,
    private httpBackend: HttpBackend
  ) {}

  getAllProductVariantOfUser(
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

  changeStatusOfProductVariant(
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

  getAllCategory(url: string, data: any): Observable<Category[]> {
    return new Observable<Category[]>(obs => {
      this._apiService.post(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.categoryList);
        }
      });
    });
  }

  getAllCountries(url: string): Observable<Country[]> {
    return new Observable<Country[]>(obs => {
      this._apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.countryList);
        }
      });
    });
  }

  getproductListDummy(url: string): Observable<ProductVariant[]> {
    return new Observable<ProductVariant[]>(obs => {
      this._apiService.get(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.productList);
        }
      });
    });
  }

  getAddressInformation(url: string, addressId): Observable<Address> {
    const param: HttpParams = new HttpParams().set("addressId", addressId);

    return new Observable<Address>(obs => {
      this._apiService.getUser(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.address);
        }
      });
    });
  }

  getBankInformation(url: string, bankId): Observable<BankDetails> {
    const param: HttpParams = new HttpParams().set("bankId", bankId);

    return new Observable<BankDetails>(obs => {
      this._apiService.getUser(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.bankDetails);
        }
      });
    });
  }

  getAllStatesOfCountry(url: string): Observable<State[]> {
    return new Observable<State[]>(obs => {
      this._apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.stateList);
        }
      });
    });
  }

  getAllCityOfState(url: string): Observable<City[]> {
    return new Observable<City[]>(obs => {
      this._apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.cityList);
        }
      });
    });
  }

  getAddressDetailsByUser(userId, url: string): Observable<Address[]> {
    return new Observable<Address[]>(obs => {
      this._apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.addressDetails);
        }
      });
    });
  }

  deleteAddressDetails(url): Observable<Address[]> {
    return new Observable<Address[]>(obs => {
      this._apiService.deleteUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.addressDetails);
        }
      });
    });
  }

  getBankDetailsByUser(userId, url: string): Observable<BankDetails[]> {
    return new Observable<BankDetails[]>(obs => {
      this._apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.bankDetails);
        }
      });
    });
  }

  deletebankDetails(url): Observable<BankDetails[]> {
    return new Observable<BankDetails[]>(obs => {
      this._apiService.deleteUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.bankDetails);
        }
      });
    });
  }

  softDeleteWishList(url): Observable<WishList[]> {
    return new Observable<WishList[]>(obs => {
      this._apiService.delete(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.wishList);
        }
      });
    });
  }

  getAllWishListOfUser(userId, url: string, body): Observable<WishList[]> {
    return new Observable<WishList[]>(obs => {
      this._apiService.post(url, body).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.wishList);
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

  saveAddressDetails(url, data): Observable<Address> {
    return new Observable<any>(obs => {
      this._apiService.postUser(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.addressDetails);
        }
      });
    });
  }

  saveBankDetails(url, data): Observable<any> {
    return new Observable<any>(obs => {
      this._apiService.postUser(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      });
    });
  }

  addProductToWishlist(url, data): Observable<any> {
    return new Observable<any>(obs => {
      this._apiService.post(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      });
    });
  }
}
