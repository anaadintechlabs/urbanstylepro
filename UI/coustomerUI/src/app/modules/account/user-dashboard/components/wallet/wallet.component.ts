import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../../../_service/http_&_login/user-service.service';
import { ToastrService } from '../../../../../../../node_modules/ngx-toastr';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.scss']
})
export class WalletComponent implements OnInit {

    public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'
  public walletDetails:any;
  public incomingTransactionList:any;
  public outgoingTransactionList:any;


  constructor(public userService:UserService,public toastr:ToastrService) { }

  ngOnInit(): void {
    this.getWalletDetailsOfUser();
  }


  getWalletDetailsOfUser()

  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.userService.getWalletDetailsOfUser(filter).subscribe(data=>{
      console.log("data us",data);
      this.walletDetails=data.data.walletDetails;
      this.incomingTransactionList=data.data.incomingTransactions;
      this.outgoingTransactionList=data.data.outgoingTransactions;
      
      console.log("data us",this.walletDetails);
    },error=>{
      console.log("error",error);
    })
  }
}
