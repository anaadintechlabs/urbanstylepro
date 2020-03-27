import { Component, OnInit, Input } from '@angular/core';
import { ProductVariant } from 'src/_modals/productVariant';
import { ApiService } from 'src/_services/http_&_login/api.service';
import { UserService } from 'src/_services/http_&_login/user-service.service';
import { User } from 'src/_modals/user.modal';

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
    private userService : UserService
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
        this.product['generatedLink'] = res.data.product;
        console.log(this.product);
      }
    })
  }

}
