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
import { User } from "src/_modals/user.modal";
import { Router } from '@angular/router';

@Component({
  selector: "app-add-product",
  templateUrl: "./add-product.component.html",
  styleUrls: ["./add-product.component.scss"],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddProductComponent implements OnInit {
  user: User;
  userId: any;
  allAttriblue: CategoryAttribute[];
  selectedProductType: string;
  selectedVariation: CategoryAttribute[];
  categorySelected: boolean = false;
  myFiles: string[] = [];
  urlArray: any = [];

  button: Button[] = [
    {
      tabIndex: 1,
      name: "Vital Information",
      visible: true,
      status: true,
      description: true
    },
    {
      tabIndex: 2,
      name: "Product Information",
      visible: true,
      status: false,
      description: false
    },
    {
      tabIndex: 3,
      name: "Advance",
      visible: false,
      status: false,
      description: false
    },
    {
      tabIndex: 3,
      name: "Media",
      visible: true,
      status: true,
      description: false
    }
    // {
    //   tabIndex : 4,
    //   name : "Extra Info",
    //   visible : true,
    //   status : true,
    //   description : false,
    // },
  ];

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
    private cdRef: ChangeDetectorRef,
    private _router : Router
  ) {
    this.setButtonWidth();
    this.vitalInfo = this._addProduct.productFormGroup;
    this.productVariantDTO = this._addProduct.productVariantDTO;
    this.productDTO = this._addProduct.productDTO;
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }

  ngOnInit() {
    console.log(this.productVariantDTO);
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user && this.user.token) {
      this.userId = this.user.id;
      this._addProduct.productFormGroup.controls.user.patchValue({
        id : this.userId
      });
    }
  }

  ngDoCheck() {
    this.setButtonWidth();
  }

  setButtonWidth() {
    let activeBtnArray = this.button.filter(el => {
      return el.visible == true;
    });
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
        (boxHeader.offsetWidth / activeBtnArray.length).toString() + "px";
    });
  }

  chooseAction(data: Button) {
    if (data.status) {
      this.button.forEach(element => {
        if (element != data) {
          element.description = false;
        } else {
          element.description = true;
        }
      });
    }
    if (data.tabIndex === 2) {
      //// do somthing for selection of product desription Tab
    } else if (data.tabIndex === 3) {
      //// do somthing for selection of product Advance Tab
    }
  }

  vitalFormStatus(status) {
    if (status) {
      this.button[1].status = true;
    } else {
      this.button[1].status = false;
    }
  }

  selectedPtoductType(value) {
    this.selectedProductType = value;
    if (value != "SIMPLE") {
      this.button[2].visible = true;
    } else {
      this.button[2].visible = false;
      //  console.log("button", this.button[2]);
    }
  }

  myFilesEmitFunction(data) {
    this.myFiles = data;
    // console.log("myfiles",this.myFiles);
  }

  myUrlArrayEmitFunction(data) {
    this.urlArray = data;
    // console.log("urlarray",this.urlArray);
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
    // console.log("productVariantDTO",this.productVariantDTO);

    while (this.productVariantDTO.length !== 0) {
      console.log(
        "data to be patched is",
        this.productVariantDTO.at(0).get("productVariant")
      );
      this.productVariantDTO.removeAt(0);
    }
    combinations.forEach(element => {
      ////here I have to set the values as well

      this.productVariantDTO.push(
        this._addProduct.initializeProductVarientDtoWithValue(
          element,
          tempAttributeData
        )
      );
    });
  }

  activeAdvanceTab(status: boolean) {
    // console.log("status is",status)
    this.button[2].status = status;
    // console.log("worked 2");
  }

  // selectedCategory(catId: number) {
  //   console.log(catId);
  //   this.categorySelected = true;
  //   this.vitalInfo.get("categoryId").setValue(catId);
  //   this._dataService
  //     .getAllVariationOfCategory("variation/getAllVariationOfCategory", catId)
  //     .subscribe(data => {
  //       console.log(data);
  //       this.allAttriblue = data;
  //     });
  // }

  saveProduct() {
    console.log("form to be saev", this._addProduct.productDTO);
    if (this._addProduct.productDTO.invalid) {
      alert("please fill the details");
    } else {
      this.vitalInfo.controls.user.patchValue({
        id: this.userId
      });

      if (this.myFiles.length == 0) {
        alert("Please select any image");
      } else {
        const productData = this._addProduct.productDTO.value;
        const formData: FormData = new FormData();
        formData.append(
          "dto",
          new Blob([JSON.stringify(productData)], { type: "application/json" })
        );
        // formData.append('dto', JSON.stringify(productData));
        for (let i = 0; i < this.myFiles.length; i++) {
          formData.append("file", this.myFiles[i]);
        }
        // console.log('form data is '+ formData);
        this._dataService
          .saveProduct("product/saveProduct", formData)
          .subscribe(data => {
            console.log(data);
            this.allAttriblue = data;
            this._router.navigateByUrl('vendor/inventory');
          });
      }
    }
  }
}

export interface Button {
  tabIndex: number;
  name: string;
  visible: boolean;
  status: boolean;
  description: boolean;
}

const addProductroutes = [
  {
    name : "Vital Info",
    url : ''
  },
  {
    name : "Product Info",
    url : ""
  },
  {
    name : "Advance",
    url : ""
  },
  {
    name : "Media",
    url : ""
  }
]
