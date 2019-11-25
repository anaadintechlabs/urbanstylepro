import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { CategoryAttribute, allAtrrtibure } from 'src/_modals/categoryAttribute.modal';

@Component({
  selector: 'product-desciprion',
  templateUrl: './add-product-desciprion.component.html',
  styleUrls: ['./add-product-desciprion.component.scss']
})
export class AddProductDesciprionComponent implements OnInit {

  @Input() categoryAttribute : CategoryAttribute[];
  @Output() productType : EventEmitter<string> = new EventEmitter<string>();
  @Output() allAttrMap : EventEmitter<CategoryAttribute[]> = new EventEmitter<CategoryAttribute[]>();
  @Output() status : EventEmitter<boolean> = new EventEmitter<boolean>()

  @Input() selectedProductType : string;
  @Input() selectedVariation : CategoryAttribute[]=[];
  constructor() { }

  ngOnInit() {
    console.log(this.categoryAttribute);
    console.log(this.selectedProductType)
  }

  onChange(value : string) : void {
    this.selectedProductType = value;
    this.flushData();
    this.productType.emit(this.selectedProductType);
  }

  addvariation(event, data:CategoryAttribute) {
    console.log("event isss",event);
    if(event.checked){
      data.allAttributeMap = new allAtrrtibure();
      data.allAttributeMap.variationName = data.attributeMaster.variationName;
      this.selectedVariation.push(data);
    } else {
      this.selectedVariation = this.selectedVariation.filter(element => {
        return element != data;
      });
    }

    this.allAttrMap.emit(this.selectedVariation);
    console.log("data to emit is ",this.selectedVariation);
  }

  ngDoCheck(){
    this.checkVariationStatus();
  }

  checkVariationStatus(){
    let status : boolean = true;
    this.selectedVariation.forEach(element => {
      if(element.allAttributeMap.variationAttribute.length>0){
        status = status && true;
      } else {
        status = status && false;
      }
    });
    //console.log("map status",status);
    if(status && this.selectedVariation.length > 0){
      this.allAttrMap.emit(this.selectedVariation);
      this.status.emit(status);
      //console.log("check1")
    } else {
      this.status.emit(false);
     // console.log("check2")
    }
  }

  addVariation(value : HTMLInputElement, index : number){
    this.selectedVariation[index].allAttributeMap.variationAttribute.push(value.value);
    console.log(this.selectedVariation);
  }

  flushData(){
    this.selectedVariation = [];
  }

}


