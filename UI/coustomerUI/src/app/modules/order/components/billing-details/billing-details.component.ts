import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { User } from 'src/_modals/user';
import { Address } from 'src/_modals/address';
import { OrderService } from 'src/_service/product/order.service';

@Component({
  selector: 'app-billing-details',
  templateUrl: './billing-details.component.html',
  styleUrls: ['./billing-details.component.scss']
})
export class BillingDetailsComponent implements OnInit {

  address : Address[] = [];
  @Output() nextStep : EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private _apiService : ApiService,
    private _userService : UserService,
    public _orderService : OrderService
  ) { }

  ngOnInit(): void {
    this.getAllAdress();
  }

  chooseAddress(add : any) {
    console.log(add);
    this._orderService.selectedAddress = add;
    this.nextStep.emit();
  }

  selectAddress() {
    this._orderService.selectedAddress = "form";
    this.nextStep.emit();
  }

  getAllAdress() {
    let user:User = this._userService.getCurrentUser();
    this._apiService.postUser(`${urls.getAllAddress}?userId=${user.id}`).subscribe(res=>{
      console.log(res.data.addressDetails);
      this.address = res.data.addressDetails;
    })
  }
}
