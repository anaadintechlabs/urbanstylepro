import { Component, OnInit, Input } from '@angular/core';
import { ProductVariant } from 'src/_modals/productVariant';
import { ApiService } from 'src/_services/http_&_login/api.service';
import { UserService } from 'src/_services/http_&_login/user-service.service';
import { User } from 'src/_modals/user.modal';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-product-view',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.scss']
})
export class ProductViewComponent implements OnInit {

  @Input() product : ProductVariant;
  user : User;

  constructor(
    private _apiService : ApiService,
    private userService : UserService,
    private toastrService : ToastrService
  ) {
    this.user = JSON.parse(this.userService.getUser());
   }

  ngOnInit() {
  }

  generateLink() {
    let url = `product/genAffiliatelink?prodVarId=${this.product.productVariantId}&userId=${this.user.id}`;
    this._apiService.get(url).subscribe(res=>{
      console.log(res);
      if(res.isSuccess) {
        if(res.data.product=='EXISTS'){
          this.toastrService.warning("Link already generated for this product!","Oops");
        } else  {
          this.product['productVarLink'] = res.data.product;
          this.toastrService.success("Link generated successfully","Success");
          console.log(this.product);
        }
      }
    })
  }

}
