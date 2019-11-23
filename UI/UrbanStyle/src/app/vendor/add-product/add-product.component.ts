import { Component, OnInit, ElementRef, RootRenderer } from '@angular/core';
import { DataService } from 'src/_services/data/data.service';
import { AddProductService } from 'src/_services/product/addProductService';
import { Category } from 'src/_modals/category.modal';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { CategoryAttribute } from 'src/_modals/categoryAttribute.modal';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  allAttriblue : CategoryAttribute[];
  productDescTab : boolean;
  dispVitalForm : boolean = true;
  dispProduDesc : boolean;
  dispAdvanceOption : boolean;
  advanceTabVisibility : boolean = false;
  private menuItemCount : number;

  private get element(): HTMLElement {
    return this.el.nativeElement;
  }

  vitalInfo : FormGroup;
  productVariantDTO : FormArray;
  constructor(
    protected _dataService : DataService,
    protected _addProduct : AddProductService,
    private el: ElementRef,
    ) {
      this.vitalInfo = this._addProduct.productFormGroup;
      this.productVariantDTO = this._addProduct.productVariantDTO;
      this.menuCount = 2;
  }

  get menuCount() : number {
    return this.menuItemCount;
  }

  set menuCount(num : number) {
    this.menuItemCount = num;
  }
    
  ngOnInit() {
    console.log(this.productVariantDTO);
  }
  

  ngDoCheck(){
    const box = this.element.querySelector('.borderBox') as HTMLElement;
    const boxHeader = this.element.querySelector('.borderBox_header') as HTMLElement;
    const boxHeaderLink = this.element.querySelectorAll('.borderBox_headerLink');

    boxHeaderLink.forEach((element)=>{
      let elem = element as HTMLElement;
      elem.style.width = (boxHeader.offsetWidth / this.menuCount).toString() + 'px';
    }); 
  }

  vitalFormStatus(status){
    if(status) {
      this.productDescTab = true;
    } else {
      this.productDescTab = false;
    }
  }

  showVitalForm(){
    this.dispVitalForm = true;
    this.dispProduDesc = false;
    this.dispAdvanceOption = false;
  }

  showProduDesc(){
    if(this.productDescTab){
      this.dispVitalForm = false;
      this.dispProduDesc = true;
      this.dispAdvanceOption = false;
    } else {

    }
  }

  showAdvanceOption(){
    if(this.advanceTabVisibility){
      this.dispVitalForm = false;
      this.dispProduDesc = false;
      this.dispAdvanceOption = true;
    }
  }

  selectedPtoductType(value) {
    if(value != 'SIMPLE') {
      this.advanceTabVisibility = true;
      this.menuCount = 3;
    } else {
      this.advanceTabVisibility = false;
      this.menuCount = 2;
    }
  }

  selectedCategory(catId:number){
    console.log(catId);
    this.vitalInfo.get('categoryId').setValue(catId);
    this._dataService.getAllVariationOfCategory('variation/getAllVariationOfCategory',catId).subscribe(data=>{
      console.log(data);
      this.allAttriblue = data;
    })
  }

}
