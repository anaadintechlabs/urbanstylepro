import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
import {
  CategoryAttribute,
  allAtrrtibure
} from "src/_modals/categoryAttribute.modal";
import { AddProductService } from "src/_services/product/addProductService";
import { FormArray } from "@angular/forms/forms";

@Component({
  selector: "product-desciprion",
  templateUrl: "./add-product-desciprion.component.html",
  styleUrls: ["./add-product-desciprion.component.scss"]
})
export class AddProductDesciprionComponent implements OnInit {
  @Input() categoryAttribute: CategoryAttribute[];
  @Output() productType: EventEmitter<string> = new EventEmitter<string>();
  @Output() allAttrMap: EventEmitter<CategoryAttribute[]> = new EventEmitter<CategoryAttribute[]>();
  @Output() status: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Input() selectedProductType: string;
  @Input() selectedVariation: CategoryAttribute[] = [];
  productVariantDTO: FormArray;

  constructor(protected _addProduct: AddProductService) {
        this.productVariantDTO = this._addProduct.productVariantDTO;
  }

  ngOnInit() {
    console.log(this.categoryAttribute);
    console.log(this.selectedProductType);
    if(this.selectedProductType==undefined)
      {
        console.log("undefineddd")
        this.selectedProductType="";
      }
  }

  onChange(value: string): void {
    console.log("value is", value);
    if (value == "SIMPLE") {
       while (this.productVariantDTO.length !== 0) {
      this.productVariantDTO.removeAt(0);
    }
    
      this.productVariantDTO.push(
        this._addProduct.initializeProductVarientDto()
      );
    }
    this.selectedProductType = value;
    this.flushData();
    this.productType.emit(this.selectedProductType);

    console.log("product variant dto us", this.productVariantDTO);
  }

  addvariation(event, data: CategoryAttribute) {
    console.log("event isss", event);
    if (event.checked) {
      data.allAttributeMap = new allAtrrtibure();
      data.allAttributeMap.variationName = data.attributeMaster.variationName;
      this.selectedVariation.push(data);
    } else {
      this.selectedVariation = this.selectedVariation.filter(element => {
        return element != data;
      });
    }

    this.allAttrMap.emit(this.selectedVariation);
    console.log("data to emit is ", this.selectedVariation);
  }

  ngDoCheck() {
    this.checkVariationStatus();
  }

  checkVariationStatus() {
    let status: boolean = true;
    console.log("this.selectedvariaion",this.selectedVariation);
    if(this.selectedVariation)
      {
    this.selectedVariation.forEach(element => {
      if (element.allAttributeMap.variationAttribute.length > 0) {
        status = status && true;
      } else {
        status = status && false;
      }
    });

    if (status && this.selectedVariation.length > 0) {
      this.allAttrMap.emit(this.selectedVariation);
      this.status.emit(status);
      console.log("check1 if");
    } else {
      this.status.emit(false);
    }
      }
  }

  addVariation(value: HTMLInputElement, index: number) {
    this.selectedVariation[index].allAttributeMap.variationAttribute.push(
      value.value
    );
    console.log(this.selectedVariation);
  }

  flushData() {
    this.selectedVariation = [];
  }
}
