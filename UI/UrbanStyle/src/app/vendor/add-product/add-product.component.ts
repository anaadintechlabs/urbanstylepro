import {
  Component,
  OnInit,
  ElementRef,
  RootRenderer,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import { DataService } from "src/_services/data/data.service";
import { AddProductService } from "src/_services/product/addProductService";
import { Category } from "src/_modals/category.modal";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
  FormArray
} from "@angular/forms";
import { CategoryAttribute } from "src/_modals/categoryAttribute.modal";

@Component({
  selector: "app-add-product",
  templateUrl: "./add-product.component.html",
  styleUrls: ["./add-product.component.scss"],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddProductComponent implements OnInit {
  allAttriblue: CategoryAttribute[];

  productDescTab: boolean;
  dispVitalForm: boolean = true;
  dispProductMedia: boolean = false;
  showAdvanceTab:boolean = false;
  dispProduDesc: boolean;
  dispAdvanceOption: boolean;
  advanceTabVisibility: boolean = false;
  selectedProductType: string;
  private menuItemCount: number;
  selectedVariation: CategoryAttribute[];

  myFiles: string[] = [];
  urlArray: any = [];

  private get element(): HTMLElement {
    return this.el.nativeElement;
  }

  productDTO: FormGroup;
  vitalInfo: FormGroup;

  productVariantDTO: FormArray;
  constructor(
    private _fb: FormBuilder,
    protected _dataService: DataService,
    protected _addProduct: AddProductService,
    private el: ElementRef,
    private cdRef: ChangeDetectorRef
  ) {
    this.vitalInfo = this._addProduct.productFormGroup;
    this.productVariantDTO = this._addProduct.productVariantDTO;
    this.productDTO = this._addProduct.productDTO;
    this.menuCount = 2;
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }
  get menuCount(): number {
    return this.menuItemCount;
  }

  set menuCount(num: number) {
    this.menuItemCount = num;
  }

  ngOnInit() {
    console.log(this.productVariantDTO);
  }

  ngDoCheck() {
    const box = this.element.querySelector(".borderBox") as HTMLElement;
    const boxHeader = this.element.querySelector(
      ".borderBox_header"
    ) as HTMLElement;
    const boxHeaderLink = this.element.querySelectorAll(
      ".borderBox_headerLink"
    );

    boxHeaderLink.forEach(element => {
      let elem = element as HTMLElement;
      elem.style.width =
        (boxHeader.offsetWidth / this.menuCount).toString() + "px";
    });
  }

  vitalFormStatus(status) {
   
    if (status) {
      this.productDescTab = true;
    } else {
      this.productDescTab = false;
    }
  }

  showVitalForm() {
    this.dispVitalForm = true;
    this.dispProduDesc = false;
    //this.dispAdvanceOption = false;
    this.dispProductMedia = false;
     this.showAdvanceTab=false;
  }

  showProductMedia() {
    this.dispVitalForm = false;
    this.dispProduDesc = false;
   // this.dispAdvanceOption = false;
    this.dispProductMedia = true;
     this.showAdvanceTab=false;
  }

  showProduDesc() {
    if (this.productDescTab) {
      this.dispVitalForm = false;
      this.dispProduDesc = true;
      this.dispAdvanceOption = false;
      this.dispProductMedia = false;
      this.showAdvanceTab=false;
    } else {
    }
  }

  showAdvanceOption() {
    if (this.advanceTabVisibility) {
      this.showAdvanceTab=true;
      this.dispVitalForm = false;
      this.dispProduDesc = false;
      this.dispAdvanceOption = true;
      this.dispProductMedia = false;
    }
  }

  selectedPtoductType(value) {
    this.selectedProductType = value;
    if (value != 'SIMPLE') {
      this.advanceTabVisibility = true;
      this.menuCount = 3;
    } else {
      this.advanceTabVisibility = false;
      this.dispAdvanceOption=false;
      this.menuCount = 2;
    }
  }

  myFilesEmitFunction(data) {
    this.myFiles = data;
    console.log("myfiles",this.myFiles);
  }

  myUrlArrayEmitFunction(data) {
    this.urlArray = data;
    console.log("urlarray",this.urlArray);
  }

  getAllAttrMap(data: CategoryAttribute[]) {
    this.selectedVariation = data;
    let tempData: any[] = [];
    var tempAttributeData: any = [];

    
    data.forEach(element => {
      tempData.push(element.allAttributeMap.variationAttribute);
      tempAttributeData.push(element.attributeMaster.id);
    });
 
    let combinations = this._addProduct.makeCombinations(tempData);
    //this.productVariantDTO = this._fb.array([]);
    while (this.productVariantDTO.length !== 0) {
      this.productVariantDTO.removeAt(0);
    }
    combinations.forEach(element => {
        //here I have to set the values as well
      this.productVariantDTO.push(
        this._addProduct.initializeProductVarientDtoWithValue(
          element,
          tempAttributeData
        )
      );
    });
  }
  activeAdvanceTab(status: boolean) {
    console.log("status is",status)
    this.dispAdvanceOption = status;
    console.log("worked 2");
  }

  selectedCategory(catId: number) {
    console.log(catId);
    this.vitalInfo.get("categoryId").setValue(catId);
    this._dataService
      .getAllVariationOfCategory("variation/getAllVariationOfCategory", catId)
      .subscribe(data => {
        console.log(data);
        this.allAttriblue = data;
      });
  }

  saveProduct() {
    console.log(
      "final FORM to be saved is ",
      this._addProduct.productDTO.value
    );
    console.log("mtfiles",this.myFiles);
console.log("myurls ",this.urlArray);
    const productData = this._addProduct.productDTO.value;
    const formData: FormData = new FormData();
    formData.append('dto', new Blob([JSON.stringify(productData)],
        {
            type: "application/json"
        }));
    // formData.append('dto', JSON.stringify(productData));

    for (let i = 0; i < this.myFiles.length; i++) {
      formData.append('file', this.myFiles[i]);
    }
    console.log('form data is '+ formData);
    this._dataService
      .saveProduct('product/saveProduct', formData)
      .subscribe(data => {
        console.log(data);
        this.allAttriblue = data;
      });
  }
}
