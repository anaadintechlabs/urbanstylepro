import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { User } from 'src/_modals/user';
import { urls } from 'src/constants/urlLists';
import { BankDetails } from 'src/_modals/bankDetails';
import { OrderService } from 'src/_service/product/order.service';

@Component({
  selector: 'app-bank-details',
  templateUrl: './bank-details.component.html',
  styleUrls: ['./bank-details.component.scss']
})
export class BankDetailsComponent implements OnInit {

  @Output() nextStep : EventEmitter<void> = new EventEmitter<void>();
  bankDetails : BankDetails[] = [];
  constructor(
    private _apiService : ApiService,
    private _userService : UserService,
    public _orderService : OrderService
  ) { }

  ngOnInit(): void {
    this.getAllBankDetails();
  }

  selectBank(){
    this._orderService.selectedBank = 'form' 
    this.nextStep.emit(); 
  }

  chooseCard(bank : any) {
    this._orderService.selectedBank = bank;
    this.nextStep.emit();
  }

  getAllBankDetails() {
    let user:User = this._userService.getCurrentUser();
    this._apiService.postUser(`${urls.getAllBankDetails}?userId=${user.id}`).subscribe(res=>{
      console.log(res.data.bankDetails);
      this.bankDetails = res.data.bankDetails;
    })
  }

}
