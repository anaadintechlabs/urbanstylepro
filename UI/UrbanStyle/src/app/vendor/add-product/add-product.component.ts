import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { AddProductService } from "src/_services/product/addProductService";
import { User } from "src/_modals/user.modal";
import { Refresh } from '../../app.component'

@Component({
  selector: "app-add-product",
  templateUrl: "./add-product.component.html",
  styleUrls: ["./add-product.component.scss"]
})
export class AddProductComponent implements OnInit {
  user: User;
  userId: any;

  constructor(
    protected _dataService: DataService,
    protected _addProduct: AddProductService,
  ) {

  }

  ngOnInit() {
    window.localStorage.setItem('addProduct', JSON.stringify(this._addProduct.productDTO.value));
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      this._addProduct.productFormGroup.controls.user.patchValue({
        id : this.userId
      });
    }
    console.log(Refresh);
    if(Refresh){
      alert('asdfwedf');
    }
  }

  ngDoCheck() {
    sessionStorage.setItem('addProduct', JSON.stringify(this._addProduct.productDTO.value));
  }

  ngOnDestroy(): void {
    this._addProduct.productDTO.reset();
    window.sessionStorage.removeItem('addProduct');
    this._addProduct.changeHeaderStaus(false);
  }
}

