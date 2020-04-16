import { Component, OnInit } from '@angular/core';
import { DataService } from "src/_services/data/data.service";
import { User } from "src/_modals/user.modal";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.scss']
})
export class WalletComponent implements OnInit {
  user: User;
  userId: any;
public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'
  public walletDetails:any;
  public incomingTransactionList:any;
  public outgoingTransactionList:any;


  constructor(public userService:DataService) { }

  ngOnInit(): void {
     this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      console.log("logged in vendor id", this.userId);
      this.getWalletDetailsOfUser(this.userId);
    }
  }


  getWalletDetailsOfUser(userId)
  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.userService.getWalletDetailsOfUser(filter,userId).subscribe(data=>{
      console.log("data us",data);
      this.walletDetails=data.walletDetails;
      this.incomingTransactionList=data.incomingTransactions;
      this.outgoingTransactionList=data.outgoingTransactions;
      
      console.log("data us",this.walletDetails);
    },error=>{
      console.log("error",error);
    })
  }

}
