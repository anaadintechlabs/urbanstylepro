import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';

@Injectable({
    providedIn : 'root'
})
export class EditProductService {
    public produtId : any;
    public variantId : any;
    public productData : any;
    productVariantDTO : FormArray;
    productVariant : FormGroup;
    productVariantFormArray : FormArray;
    saleSelect : boolean;
    selectedVariation = [1,2]

    constructor(
        public _fb :FormBuilder
    ) {
        this.productVariantDTO = this._fb.array([]);
        this.productVariant = this._fb.group({
            productVariantForm : this._fb.array([])
        })
        
    }

    public productForm = new FormGroup({
        user: new FormGroup({
          id: new FormControl("")
        }),
        productId: new FormControl("", []),
        productCode: new FormControl("", [
          Validators.maxLength(40)
        ]),
        categoryId: new FormControl("", []),
        productName: new FormControl("", [
          ,
          Validators.maxLength(100)
        ]),
        brandName: new FormControl("", [Validators.maxLength(80)]),
        manufacturer: new FormControl("", [Validators.maxLength(80)]),
        longDescription: new FormControl("", []),
        features: new FormControl("", []),
        legalDisclaimer : new FormControl("",[]),
        coverPhoto: new FormControl("", []),
        defaultSize : new FormControl("",[]),
        defaultColor : new FormControl("",[]),
        productIdType: new FormControl(),
      });

      public productVariantForm : FormGroup;

      variantValue(element) : FormGroup {
        let form = new FormGroup({
            sku: new FormControl(element.sku, []),
            variantName : new FormControl(element.variantName,[]),
            variantCode : new FormControl(element.variantCode,[]),
            productIdType : new FormControl(element.productIdType,[]),
            displayPrice: new FormControl(element.displayPrice, []),
            salesPrice: new FormControl(element.salesPrice, []),
            salesStartDate: new FormControl(element.salesStartDate, []),
            salesEndDate: new FormControl(element.salesEndDate, []),
            salesQuantity: new FormControl(element.salesQuantity, []),
            manufacturerSuggesstedPrice: new FormControl(element.manufacturerSuggesstedPrice, []),
            totalQuantity: new FormControl(element.totalQuantity, []),
            reservedQuantity: new FormControl(element.reservedQuantity),
          });
          return form;
      }

      public initializeProductVarientDto(): FormGroup {
        let productVarientDto: FormGroup;
        let myMap = {};
        myMap["0"] = "";
        productVarientDto = this._fb.group({
          attributesMap: new FormControl(myMap),
          productVariant: this.productVariantForm
        });
        return productVarientDto;
      }

      initializeData() {
          this.productForm.patchValue(this.productData.product);
          this.productData.productVariantDTO.forEach(element => {
            //   this.productVariantDTO.push(this.initializeProductVarientDto());
              this.productVariantFormArray = this.productVariant.get('productVariantForm') as FormArray; 
              this.productVariantFormArray.push(this.variantValue(element.productVariant));
          });
      }
}