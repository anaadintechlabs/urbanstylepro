import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { AddProductService } from "src/_services/product/addProductService";
import { User } from "src/_modals/user.modal";

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
    private cdRef: ChangeDetectorRef,
  ) {
    if(window.localStorage.getItem('addProduct')){
      this._addProduct.productDTO.patchValue(JSON.parse(window.localStorage.getItem('addProduct')));
    }
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
  }

  ngDoCheck() {
    localStorage.setItem('addProduct', JSON.stringify(this._addProduct.productDTO.value));
  }

  ngOnDestroy(): void {
    //Called once, before the instance is destroyed.
    //Add 'implements OnDestroy' to the class.
    this._addProduct.productDTO.reset();
    window.localStorage.removeItem('addProduct')
  }
}

