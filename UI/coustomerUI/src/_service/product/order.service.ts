import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { CartItem } from 'src/_modals/cartItem';
import { UserService } from '../http_&_login/user-service.service';
import { ApiService } from '../http_&_login/api.service';
import { User } from 'src/_modals/user';
import { Observable } from '../../../node_modules/rxjs';
import { HttpParams } from "@angular/common/http";

@Injectable({
    providedIn:'root'
})
export class OrderService {

    public SinglecheckoutItem : CartItem;
    public selectedAddress : any;
    public selectedBank : any;
    private user : User;

    public addressForm : FormGroup = new FormGroup({
        addressOne : new FormControl(''),
        addressTwo : new FormControl(''),
        user : new FormControl(''),
        country : new FormControl(''),
        state : new FormControl(''),
        cite : new FormControl(''),
        zip : new FormControl(''),
    });

    public bankInfo : FormGroup;

    constructor(
        private _fb : FormBuilder,
        private _userService : UserService,
        private _apiService : ApiService
    ) {
        this.user = this._userService.getCurrentUser();

        this.bankInfo = this._fb.group({
            cardNumber : new FormControl(''),
            year : new FormControl(''),
            month : new FormControl(''),
            typeOfCard : new FormControl('CREDIT'),
            cvv : new FormControl(''),
            user : this._fb.group({
                id : new FormControl(this.user.id),
            })
        })
    }

    placeOrder(list:any) {
        let body = {
            "userId": this.user.id,
            "paymentType":"CREDIT_CARD",
            "from":"Sanchit",
            "to":"Paras",
        };

        if(this.selectedAddress == 'form'){
            body['address'] = this.addressForm.value
        } else {
            body['address'] = {'id' : this.selectedAddress.id}
        }

        if(this.selectedAddress == 'form'){
            body['bankInfo'] = this.bankInfo.value;
        } else {
            body['bankInfo'] = {'id' : this.selectedBank.id}
        }

        body['userOrderList'] = list;

        console.log('body is', body);
        this._apiService.postOrder('api/saveOrder',body).subscribe(res=>{
            console.log(res);
        })
    }

    getOrderOfUser(filter)
    {
        console.log("filter is",filter)
        if(filter)
        {
        let currunt_user = JSON.parse(this._userService.getUser());
        let url='api/getOrderByUser?userId='+currunt_user.id;
        return new Observable<any>(obs => {
            this._apiService.postOrder(url,filter).subscribe(res=>{
                return obs.next(res);
              
          });
      });  
    }
    }


    cancelOrderByUser(orderId,orderProductId)
    {
        let currunt_user = JSON.parse(this._userService.getUser());
        let url='api/cancelOrderByUser?userId='+currunt_user.id+'&orderId='+orderId+'&orderProductId='+orderProductId;
        return new Observable<any>(obs => {
            this._apiService.getOrder(url).subscribe(res=>{
                return obs.next(res);
              
          });
      });  
    }


    returnOrderByUser(orderId,orderProdId,reason)
    {
        let currunt_user = JSON.parse(this._userService.getUser());
        let url='api/returnOrderByUser?userId='+currunt_user.id+'&orderId='+orderId+'&orderProdId='+orderProdId+'&reason='+reason;
        return new Observable<any>(obs => {
            this._apiService.getOrder(url).subscribe(res=>{
                return obs.next(res);
              
          });
      });   
    }


    getOrderById(orderId)
    {
        let url='api/returnOrderByUser?userOrderId='+orderId;
        return new Observable<any>(obs => {
            this._apiService.getOrder(url).subscribe(res=>{
                return obs.next(res);
              
          });
      });   
    }

    getAllDetailOfReturn(returnId)
    {
        let url='api/getAllDetailOfReturn?returnId='+returnId;
        return new Observable<any>(obs => {
            this._apiService.getOrder(url).subscribe(res=>{
                return obs.next(res);
              
          });
      });   
    }

    getReturnByUser(filter)
    {
        console.log("filter is",filter)
        if(filter)
        {
        let currunt_user = JSON.parse(this._userService.getUser());
        let url='api/getReturnByUser?userId='+currunt_user.id;
        return new Observable<any>(obs => {
            this._apiService.postOrder(url,filter).subscribe(res=>{
                return obs.next(res.data);
              
          });
      });  
    }
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
}