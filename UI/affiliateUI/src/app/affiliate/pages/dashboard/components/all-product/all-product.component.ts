import { Component, OnInit } from '@angular/core';
import { ProductVariant } from 'src/_modals/productVariant';
import { DataService } from 'src/_services/data/dataService';
import { User } from 'src/_modals/user.modal';

@Component({
  selector: 'app-all-product',
  templateUrl: './all-product.component.html',
  styleUrls: ['./all-product.component.scss']
})
export class AllProductComponent implements OnInit {
  
  productList : ProductVariant[] = [];
  user : User;
  userId :any;
  constructor(
    private dataService : DataService
  ) {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user.token) {
      this.userId = this.user.id;
      // this.getAllActiveOrInactiveProductVariantOfUser('ACTIVE')
    }
   }

  ngOnInit() {
  }

  getAllActiveOrInactiveProductVariantOfUser(status) {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    let url =
      "product/getAllActiveOrInactiveProductVariantOfUser?userId=" +
      this.userId +
      "&status=" +
      status;
    this.dataService
      .getAllActiveOrInactiveProductVariantOfUser(url, body)
      .subscribe(
        data => {
          this.productList = data;
        },
        error => {
          console.log("error======", error);
        }
      );
    console.log(this.productList);
  }

}
