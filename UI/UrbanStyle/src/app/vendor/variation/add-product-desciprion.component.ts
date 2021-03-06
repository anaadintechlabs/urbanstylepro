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
    public _addProduct: AddProductService,
    private _router: Router
  ) { }

  ngOnInit() { }

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

    if(this._addProduct.selectedVariation.length == 0){
     this._addProduct.productVariantDTO.push(this._addProduct.initializeProductVarientDto());
    } else {
      this._addProduct.productVariantDTO.removeAt(0)
    }

  }

  getAllAttrMap() {
    let data = this._addProduct.selectedVariation;
    let tempData: any[] = [];
    var tempAttributeData: any = [];
    console.log("maiking", data)
    if (data && data.length > 0) {
      let check = true;
      data.forEach(element => {
        //This check will be removed if previous bug solved
        if (element.allAttributeMap.variationAttribute.length > 1) {
          if(element.allAttributeMap.variationAttribute[element.allAttributeMap.variationAttribute.length-1] == ""){
            element.allAttributeMap.variationAttribute.pop();
          }
        }
        console.log(element.allAttributeMap.variationAttribute)
        if (element.allAttributeMap.variationAttribute.indexOf("") > -1) {
          check = false;
        }
        else {
          tempData.push(element.allAttributeMap.variationAttribute);
          tempAttributeData.push(element.attributeMaster.id);
        }
      });

      if (check) {
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
      else {
        alert("Please fill all th value")
      }
    }
    else {
      alert("Please select any variantion")
    }
    console.log(this._addProduct.productDTO);
  }

  addVariation(ev, index: number, index2: number) {
    let value = ev.target.value;
    let ele = ev.path[0] as HTMLElement;
    if((this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.length - 1) == index2){
      if(value != '') {
        this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[index2] = value;
        setTimeout(() => {
          this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.push("");
          this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[(index2+1)] = "";
          ele.focus();
        }, 0);
      }
    } else {
      if(value != '') {

      } else {
        this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.splice(index2, 1)
      }
    }
    // console.log(value, index, index2);
    // if (value != '') {
    //   console.log(value, index, index2);
    //   this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[index2] = value;
    //   setTimeout(() => {
    //     this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute.push("");
    //     this._addProduct.selectedVariation[index].allAttributeMap.variationAttribute[index2+1] = "";
    //   }, 200);
    // }
    // else {
    
    // }

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
    console.log("variation vale", this._addProduct.productVariantDTO.value);
    if (this._addProduct.productVariantDTO.length == 0) {
      alert("Please enter variant details");
    }
    else {
      let check = true;
      //If any value in vairant details is not filled then do not go ahead
      this._addProduct.productVariantDTO.value.forEach(element => {
        Object.keys(element.productVariant).forEach(variantKey => {
          if (element.productVariant[variantKey] == undefined || element.productVariant[variantKey] == "") {
            check = false;

          }
        });
      });

      if (check) {
        this._router.navigateByUrl('/vendor/addProduct/imageUpload');
      }
      else {
        alert("Please fill all the details")
      }
    }

  }
  backButton() {
    this._router.navigateByUrl('/vendor/addProduct/vitalInfo');
  }
}
