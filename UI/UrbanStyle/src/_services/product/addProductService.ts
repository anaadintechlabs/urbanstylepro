import { Injectable } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray
} from "@angular/forms";
import { CategoryAttribute } from "src/_modals/categoryAttribute.modal";
import { ApiService } from "../http_&_login/api.service";
import { HttpParams } from '@angular/common/http';
import { MetaInfo } from 'src/_modals/productMeta';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";

@Injectable({
  providedIn: "root"
})
export class AddProductService {
  private header_status : BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  readonly headerStatus$ = this.header_status.asObservable();

  //// edit or add status
  private product_status : BehaviorSubject<string> = new BehaviorSubject<string>('');
  readonly productStatus$ = this.product_status.asObservable();

  public selectedVariation: CategoryAttribute[] = [];
  public categoryAttribute: CategoryAttribute[] = [];
  public selectedProductType: string = "ADVANCE";
  selectedCatID : number = 0;
  myFiles: string[] = [];
  urlArray: any = [];

  get productDescFormGroup() : FormGroup {
    return this.productDTO.get('productDesc') as FormGroup
  }

  get productFormGroup(): FormGroup {
    return this.productDTO.get("product") as FormGroup;
  }

  get productVariantDTO(): FormArray {
    return this.productDTO.get("productVariantDTO") as FormArray;
  }

  get getProductMetaAllInfo(): FormArray {
    return this.productDTO.get("productMetaInfo") as FormArray;
  }

  
  constructor(
    protected _fb: FormBuilder,
    private _apiService: ApiService,
    private _router : Router,
    private toastr: ToastrService
  ) {
    this.productDTO = this._fb.group({
      product: this.productForm,
      productDesc : this.productDescription,
      productVariantDTO: this._fb.array([]),
      productMetaInfo : this._fb.array([])
    });

    console.log(this.productDTO.value);
  }

  /////// FormGroup for productmeta (key value pair)
  public productMetaForm = new FormGroup({
    metaKey: new FormControl("", [Validators.required]),
    metaValue: new FormControl("", [Validators.required])
  });

  public productDescription = new FormGroup({
    feature : new FormControl(''),
    description: new FormControl('')
  })

  /////// product variation formGroup
  public productVariantForm = new FormGroup({
    sku: new FormControl("", [Validators.required]),
    displayPrice: new FormControl("", [Validators.required]),
    actualPrice: new FormControl("", [Validators.required]),
    discountPrice: new FormControl("", [Validators.required]),
    totalQuantity: new FormControl("", [Validators.required]),
    reservedQuantity: new FormControl("0")
  });

  /////// formGroup for vital information for product
  public productForm = new FormGroup({
    user: new FormGroup({
      id: new FormControl("")
    }),
    productId: new FormControl("", []),
    productCode: new FormControl("", [Validators.required,Validators.maxLength(40)]),
    categoryId: new FormControl("", [Validators.required]),
    productName: new FormControl("", [Validators.required,Validators.maxLength(100)]),
    brandName: new FormControl("", [,Validators.maxLength(80)]),
    manufacturer: new FormControl("", [,Validators.maxLength(80)]),
    coverPhoto: new FormControl("", []),
    productIdType:new FormControl('ASIN')
  });

  product: FormGroup;
  productDTO: FormGroup;
  uploadedPhoto : string[] = [];
  metaList: MetaInfo[];


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

  intializeproductMetaInfo() : FormGroup {
    return this._fb.group({
      metaKey : new FormControl(''),
      metaValue : new FormControl('')
    })
  }

  public initializeProductVarientDtoWithValue(
    data,
    tempAttributeData
  ): FormGroup {
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
        reservedQuantity: new FormControl("0")
      })
    });
    return productVarientDto;
  }

  public addMetaInfo(): FormGroup {
    return this.productMetaForm;
  }

  selectedCategory(catId: number) {
    this.selectedCatID = catId;
    console.log(catId);
    const param: HttpParams = new HttpParams().set("categoryId", catId.toString());
    // this.vitalInfo.get("categoryId").setValue(catId);
    this._apiService.get("variation/getAllVariationOfCategory", param).subscribe(res => {
      if (res.isSuccess) {
        this.categoryAttribute = res.data.variationList;
        this.metaList = res.data.metaList;
        this.getProductMetaAllInfo.clear();
        this.getmetaInfo();
      }
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
          result.push([arr[0][j] + "," + allCasesOfRest[i]]);
        }
      }
      return result;
    }
  }

  saveChanges() {
    this.uploadedPhoto = this.myFiles;
    let url : string = '';
    const frmData = new FormData();  
    for (var i = 0; i < this.uploadedPhoto.length; i++) {  
      frmData.append("file", this.uploadedPhoto[i]);  
    } 
    frmData.append("productDTOString", JSON.stringify(this.productDTO.value));
    this.productStatus$.subscribe(data=>{
      if(data == 'EDIT') {
        url = "product/updateProduct";  
      } else {
        url = "product/saveProduct";
      }
      this._apiService
      .postWithMedia(url, frmData)
      .subscribe(res => {
        console.log("save done");
        this._router.navigateByUrl('/vendor/inventory');
         this.toastr.success('Product saved successfully', 'Success');
    },error=>{
      this.toastr.success('Something went wrong!', 'Failure');
    });
    })
  }

  getMetaInfoArray() {
    return this.productDTO.get('productMeta') as FormArray;
  }

  getmetaInfo() {
    console.log(this.metaList);
    if(!this.metaList.length){
      this.getProductMetaAllInfo.push(this.intializeproductMetaInfo());
    } else {
      this.metaList.forEach(element=>{
        let tempGrp = this._fb.group({
          metaKey : new FormControl(element.metaKey),
          metaValue : new FormControl(element.metaValue)
        });
        this.getProductMetaAllInfo.push(tempGrp);
      })
    }
  }

  onSelectFile(event) {
    for (var i = 0; i < event.target.files.length; i++) {
      if (event.target.files[i].size <= 2048000) {
        this.myFiles.push(event.target.files[i]);
        var reader = new FileReader();
        reader.readAsDataURL(event.target.files[i]);
        reader.onload = event => {
          this.urlArray.push(reader.result);
        };
      } else {
        alert("Please select image less than 2MB.");
      }
    }
    console.log("total imags" + this.urlArray);
  }

  uploadPhoto(myFiles) {
    this.uploadedPhoto = myFiles;
    this.saveChanges();
  }

  changeHeaderStaus(value : boolean){
    this.header_status.next(value);
  }

  changeProductStaus(value : string){
    this.product_status.next(value);
  }

  flushData() {
    this.productDTO.reset();
    this.productVariantDTO.clear();
    this.getProductMetaAllInfo.clear();
    this.myFiles = [];
    this.urlArray =[];
    window.sessionStorage.removeItem('addProduct');
  }
}
