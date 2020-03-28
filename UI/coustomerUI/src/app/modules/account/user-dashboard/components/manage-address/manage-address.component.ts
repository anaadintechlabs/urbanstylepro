import { Component, OnInit, ViewChild } from "@angular/core";
import { UserService } from '../../../../../../_service/http_&_login/user-service.service';



@Component({
  selector: 'app-manage-address',
  templateUrl: './manage-address.component.html',
  styleUrls: ['./manage-address.component.scss']
})
export class ManageAddressComponent implements OnInit {



  addressDetails: any = [];
  addressCount: number;
  public userId: number;
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;

  constructor(public userService:UserService ) {
      this.getAddressDetailsByUser();
  }

  ngOnInit() {}

  getAddressDetailsByUser() {
    this.userService
      .getAddressDetailsByUser( )
      .subscribe(
        data => {
          this.addressDetails = data.addressDetails;
          console.log("Address",)
        },
        error => {
          console.log("error======", error);
        }
      );
  }

 
  deleteAddressDetails(address) {
   
    
    this.userService.deleteAddressDetails(address.id).subscribe(
      data => {
        this.addressDetails = data.addressDetails;
      },
      error => {
        console.log("error======", error);
      }
    );
  }

}
