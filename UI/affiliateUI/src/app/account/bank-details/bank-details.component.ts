import { Component, OnInit, ViewChild } from "@angular/core";

import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatPaginator,
  MatSort,
  MatTableDataSource
} from "@angular/material";
// import { DataService } from "src/_services/data/data.service";
import { Router, ActivatedRoute } from "@angular/router";
import { User } from 'src/_modals/user.modal';

@Component({
  selector: 'app-bank-details',
  templateUrl: './bank-details.component.html',
  styleUrls: ['./bank-details.component.scss']
})
export class BankDetailsComponent implements OnInit {

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  displayedColumns: string[] = ["group", "groupDescription", "actions"];
  bankDetails: any = [];
  bankDetailCount: number;
  public userId: any=1;
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;
  user : User

  constructor(
        // private dataService: DataService,
        private route: ActivatedRoute,
        private router: Router
  ) {
    //getUserId Dynamic
    this.user =  JSON.parse(window.localStorage.getItem('user'));
    if(this.user.token){
      this.userId = this.user.id;
      console.log(this.user);
      console.log(this.userId);
      // this.getBankDetailsByUser(this.userId);
    }
  }

  ngOnInit() {}

  // getBankDetailsByUser(userId) {
  //   this.dataService
  //     .getBankDetailsByUser(
  //       userId,
  //       "api/getBankDetailsByUser?userId=" + userId
  //     )
  //     .subscribe(
  //       data => {
  //         this.bankDetails = data;
  //         this.ELEMENT_DATA = this.bankDetails;
  //         this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  //       },
  //       error => {
  //         console.log("error======", error);
  //       }
  //     );
  // }

  navigateToEditOrViewbankDetail(action, bankDetail) {
    if (this.userId) {
      this.router.navigateByUrl(
        "vendor/account/bank_details/"+action+
          "/" +
          bankDetail.id
      );
    }
  }

  // deletebankDetails(bankDetail) {
  //   let url =
  //     "api/deleteBankDetails?userId=" +
  //     this.userId +
  //     "&bankId=" +
  //     bankDetail.id +
  //     "&status=0";
  //   this.dataService.deletebankDetails(url).subscribe(
  //     data => {
  //       this.bankDetails = data;
  //       this.ELEMENT_DATA = this.bankDetails;
  //       this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  //     },
  //     error => {
  //       console.log("error======", error);
  //     }
  //   );
  // }

  navigateToAddBankDetail() {
    if (this.userId) {
      this.router.navigateByUrl(
        "vendor/account/bank_details/add"
      );
    }
  }


  pageEvent(event) {
   
  }
}
