import { Component, OnInit } from "@angular/core";
import {
  CategoryAttribute,
  allAtrrtibure
} from "src/_modals/categoryAttribute.modal";
import { AddProductService } from "src/_services/product/addProductService";
import { Router } from '@angular/router';

@Component({
  selector: "product-desciprion",
  templateUrl: "./add-product-desciprion.component.html",
  styleUrls: ["./add-product-desciprion.component.scss"]
})
export class AddProductDesciprionComponent implements OnInit {
  constructor(
    protected _addProduct: AddProductService,
    private _router : Router
  ) {}

  ngOnInit() {}

  onChange(value: string): void {
    console.log("value is", value);
    if (value == "SIMPLE") {
      while (this._addProduct.productVariantDTO.length !== 0) {
        this._addProduct.productVariantDTO.removeAt(0);
      }

      this._addProduct.productVariantDTO.push(
        this._addProduct.initializeProductVarientDto()
      );
    }
    this._addProduct.selectedProductType = value;
  }

  addvariation(event, data: CategoryAttribute) {
    console.log("add variation called")

    if (event.target.checked) {
      data.allAttributeMap = new allAtrrtibure();
      data.allAttributeMap.variationName = data.attributeMaster.variationName;
      data.allAttributeMap.variationAttribute.push('');
      this._addProduct.selectedVariation.push(data);
      data.attributeMaster.checked = true;
    } else {
      data.attributeMaster.checked = false;
      this._addProduct.selectedVariation = this._addProduct.selectedVariation.filter(
        element => {
          return element != data;
        }
      );
    }
    
  }

  getAllAttrMap() {
    let data = this._addProduct.selectedVariation;
    let tempData: any[] = [];
    var tempAttributeData: any = [];
    console.log("maiking",data)
    if(data && data.length>0)
      {
        let check=true;
    data.forEach(element => {
      //This check will be removed if previous bug solved
      if(element.allAttributeMap.variationAttribute.length > 1) {
        element.allAttributeMap.variationAttribute.pop();
      }
      console.log(element.allAttributeMap.variationAttribute)
      if(element.allAttributeMap.variationAttribute.indexOf("")>-1)
        {
         check=false;
        }
        else{
      tempData.push(element.allAttributeMap.variationAttribute);
      tempAttributeData.push(element.attributeMaster.id);
        }
    });

    if(check)
      {
    let combinations = this._addProduct.makeCombinations(tempData);

    while (this._addProduct.productVariantDTO.length !== 0) {
    
      this._addProduct.productVariantDTO.removeAt(0);
    }
    combinations.forEach(element => {
      ////here I have to set the values as well

      this._addProduct.productVariantDTO.push(
        this._addProduct.initializeProductVarientDtoWithValue(
          element,
          tempAttributeData
        )
      );
    });
      }
else{
  alert("Please fill all th value")
}
      }
    console.log(this._addProduct.productDTO);
  }

  addVariation(value, index: number, index2:number) {
     console.log(value,index,index2);
    if(value!='')
      {
    this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.push("");
    // this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.push("");
    console.log(value,index,index2);
    this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[index2] = value;
   console.log(this._addProduct.selectedVariation);    
      }
  else
    {
      // console.log(this._addProduct.selectedVariation)
      if(this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.length>1)
        {
       this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.splice(index2,1)
       console.log(this._addProduct.selectedVariation)
        }
      }
    //this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[index2+1] = "";
   
  }

  removeAttribure(map, index) {
    this._addProduct.selectedVariation[
      index
    ].allAttributeMap.variationAttribute = this._addProduct.selectedVariation[
      index
    ].allAttributeMap.variationAttribute.filter(element => {
      return element != map;
    });
  }

  nextStep() {
    // this._router.navigateByUrl('/vendor/addProduct/extraDetails');
    this._router.navigateByUrl('/vendor/addProduct/imageUpload');
  }
  backButton()
  {
    this._router.navigateByUrl('/vendor/addProduct/vitalInfo');
  }
}
