import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { CartItem } from 'src/_modals/cartItem';
import { UserService } from '../http_&_login/user-service.service';
import { ApiService } from '../http_&_login/api.service';
import { User } from 'src/_modals/user';

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
        this.user = JSON.parse(this._userService.getUser());

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
            body['bankCardDetails'] = this.bankInfo.value;
        } else {
            body['bankCardDetails'] = {'id' : this.selectedBank.id}
        }

        body['userOrderList'] = list;

        console.log('body is', body);
        this._apiService.postOrder('api/saveOrder',body).subscribe(res=>{
            console.log(res);
        })
    }
}