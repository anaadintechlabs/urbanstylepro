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
import { MetaInfo } from 'src/_modals/productMeta';
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
  getAllCategories(
    url: string,
    data: any
  ): Observable<Category[]> {
    return new Observable<Category[]>(obs => {
      this._apiService.post(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.categoryList);
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

  updateVarientDTO(url,data):Observable<any> {
    return new Observable<any>(obs=>{
        this._apiService.post(url,data).subscribe(res=>{
          obs.next(res);
        })
    });
  }

changeStatusOfCategory(
    url: string,
    data: any
  ): Observable<Category[]> {
    return new Observable<Category[]>(obs => {
      this._apiService.post(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.categoryList);
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

  getAllProductOfUser(data,body) : Observable<any>{
    return new Observable<any>(obs => {
      this._apiService.post(`product/getAllProductOfUser?userId=${data}`,body).subscribe(res=> {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      },(err)=>{
        console.log(err);
      });
    });
  }

  //get all order of vendor
  getAllOrderOfVendor(vendorId,body,url)
  {

    //  const param: HttpParams = new HttpParams().set("vendorId", vendorId);
    // console.log(param);
    url=url+"?vendorId="+vendorId;
    return new Observable<any>(obs => {
      this._apiService.postOrder(url,body).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      });
    });
  }

  //all return of vendor
  getAllReturnOfVendor(vendorId,filter,type,url)
  {

      url=url+'?vendorId='+vendorId+'&type='+type;
    //  const param: HttpParams = new HttpParams().set("vendorId", vendorId);
    // console.log(param);
    return new Observable<any[]>(obs => {
      this._apiService.postOrder(url,filter).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.returnList);
        }
      });
    });
  }


  changeStatusOfReturn(returnId,status,url)
  {
    const param: HttpParams = new HttpParams().set("returnId", returnId).set("status",status);   
 return new Observable<any[]>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.orderList);
        }
      });
    });
  }

  //get all order of vendor by status
  getAllOrderOfVendorByStatus(vendorId,status,body,url)
  {
    // const param: HttpParams = new HttpParams().set("vendorId", vendorId).set("status",status);
//    console.log(param);
        url=url+"?vendorId="+vendorId+'&status='+status;

    return new Observable<any>(obs => {
      this._apiService.postOrder(url,body).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      });
    });

  }


  getOrderProductForVendor(orderId,orderProductId,vendorId,url)
  {
const param: HttpParams = new HttpParams().set("vendorId", vendorId).set("orderId",orderId).set("orderProductId",orderProductId);   

 return new Observable<any>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data);
        }
      });
    });
  }

  changeStatusOfCompleteOrder(status,orderId,url)
  {
    const param: HttpParams = new HttpParams().set("orderId", orderId).set("status",status);   
 return new Observable<any[]>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.orderList);
        }
      });
    });
  }

  changeStatusOfPartialOrder(status,orderProdId,url,trackingId,trackingLink)
  {
    const param: HttpParams = new HttpParams().set("orderProdId", orderProdId).set("status",status).set("trackingId",trackingId).set("trackingLink",trackingLink);   
 return new Observable<any[]>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.orderList);
        }
      });
    });
  }


  cancelOrderByUser(userId,orderId,orderProductId,url)
  {
    const param: HttpParams = new HttpParams().set("orderId", orderId).set("userId",userId).set("orderProductId",orderProductId);   
    return new Observable<any[]>(obs => {
         this._apiService.getOrder(url, param).subscribe(res => {
           if (res.isSuccess) {
             obs.next(res.data.orderList);
             

           }
         });
       });
  }

  returnOrderByUser(userId,orderId,orderProdId,reason,url)
  {
    const param: HttpParams = new HttpParams().set("orderId", orderId).set("orderProdId",orderProdId).set("userId",userId).set("reason",reason);   
    return new Observable<any[]>(obs => {
         this._apiService.getOrder(url, param).subscribe(res => {
           if (res.isSuccess) {
             obs.next(res.data.orderList);
           }
         });
       });
  }

  completeOrderByAdmin(userId,orderId,status,url)
  {
    const param: HttpParams = new HttpParams().set("orderId", orderId).set("userId",userId).set("status",status);   
    return new Observable<any[]>(obs => {
         this._apiService.getOrder(url, param).subscribe(res => {
           if (res.isSuccess) {
             obs.next(res.data.orderList);
           }
         });
       });
  }

  getLastOrdersForVendor(url, offset, vendorId, status) {
    const param: HttpParams = new HttpParams().set("offset", offset).set("vendorId", vendorId).set("status", status);
    return new Observable<any[]>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.lastOrders);
        }
      });
    });
  }

  getReturnForVendor(url, vendorId, filter) {
    url=url+'?vendorId='+vendorId;
    return new Observable<any[]>(obs => {
      this._apiService.postOrder(url,filter).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.returnList);
        }
      });
    });
  }


  getLast5ProductReviewsOfVendor(url, offset, vendorId) {
    const param: HttpParams = new HttpParams().set("offset", offset).set("vendorId", vendorId);
    return new Observable<any[]>(obs => {
      this._apiService.get(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.reviewList);
        }
      });
    });
  }

  getWalletByUser(url,userId) {
    const param: HttpParams = new HttpParams().set("userId", userId);
    return new Observable<any[]>(obs => {
      this._apiService.getOrder(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.walletDetails);
        }
      });
    });
  }

}
