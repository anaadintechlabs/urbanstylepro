import { Injectable } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray
} from "@angular/forms";
import { CategoryAttribute } from 'src/_modals/categoryAttribute.modal';
import { DataService } from '../data/data.service';

@Injectable({
  providedIn: "root"
})
export class AddProductService {

  public selectedVariation : CategoryAttribute[] = [];
  public categoryAttribute: CategoryAttribute[] = [];
  public selectedProductType : string = 'ADVANCE';
  /////// FormGroup for productmeta (key value pair)
  public productMetaForm = new FormGroup({
    metaKey: new FormControl("", [Validators.required]),
    metaValue: new FormControl("", [Validators.required])
  });

  /////// product variation formGroup
  public productVariantForm = new FormGroup({
    sku: new FormControl("", [Validators.required]),
    displayPrice: new FormControl("", [Validators.required]),
    actualPrice: new FormControl("", [Validators.required]),
    discountPrice: new FormControl("", [Validators.required]),
    totalQuantity: new FormControl("", [Validators.required]),
    reservedQuantity: new FormControl("", )
  });

  /////// formGroup for vital information for product
  public productForm = new FormGroup({
    user: new FormGroup({
      id: new FormControl("")
    }),
    productId: new FormControl("", []),
    productCode: new FormControl("", [Validators.required]),
    categoryId: new FormControl("", [Validators.required]),
    productName: new FormControl("", [Validators.required]),
    brandName: new FormControl("", []),
    manufacturer: new FormControl("", []),
    coverPhoto: new FormControl("", []),
     
  });

  product: FormGroup;
  productDTO: FormGroup;

  constructor(
    protected _fb: FormBuilder,
    private _dataService : DataService
  ) {
    this.productDTO = this._fb.group({
      product: this.productForm,
      productVariantDTO: this._fb.array([this.initializeProductVarientDto()]),
    });
    // this will be added later
    //  productMetaInfo: this._fb.array([this.addMetaInfo()])
    console.log(this.productDTO.value);
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

  public initializeProductVarientDtoWithValue(
    data,
    tempAttributeData
  ): FormGroup {
    console.log("tempAttributeData", data);
    let myMap = {};
    let productVarientDto: FormGroup;
    let commaSepratedString = data[0];
    let commaSepratedData = commaSepratedString.split(",");
    for (let i in commaSepratedData) {
      myMap[tempAttributeData[i]] = commaSepratedData[i];
    }
    productVarientDto = this._fb.group({
      attributesMap: new FormControl(myMap),
      productVariant: new FormGroup({
        sku: new FormControl("", [Validators.required]),
        displayPrice: new FormControl("", [Validators.required]),
        actualPrice: new FormControl("", [Validators.required]),
        discountPrice: new FormControl("", [Validators.required]),
        totalQuantity: new FormControl("", [Validators.required]),
        reservedQuantity: new FormControl("", )
      })
    });
    return productVarientDto;
  }

  public addMetaInfo(): FormGroup {
    return this.productMetaForm;
  }

  get productFormGroup(): FormGroup {
    return this.productDTO.get("product") as FormGroup;
  }

  get productVariantDTO(): FormArray {
    return this.productDTO.get("productVariantDTO") as FormArray;
  }

  selectedCategory(catId: number) {
    console.log(catId);
    // this.vitalInfo.get("categoryId").setValue(catId);
    this._dataService
      .getAllVariationOfCategory("variation/getAllVariationOfCategory", catId)
      .subscribe(data => {
        console.log(data);
        this.categoryAttribute = data;
      });
  }


  makeCombinations(arr) {
    if (arr.length == 1) {
      return arr[0];
    } else {
      var result = [];
      var allCasesOfRest = this.makeCombinations(arr.slice(1)); // recur with the rest of array

      for (var i = 0; i < allCasesOfRest.length; i++) {
        for (var j = 0; j < arr[0].length; j++) {

          result.push([arr[0][j]+ ","+allCasesOfRest[i]]);
        }
      }
      return result;            
    }
  }
}
