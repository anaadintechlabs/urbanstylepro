import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Category } from "../../../_modals/category.modal";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';
import { DataService } from 'src/_services/data/data.service';

@Component({
  selector: 'categoty-selection',
  templateUrl: './categoty-selection.component.html',
  styleUrls: ['./categoty-selection.component.scss']
})
export class CategotySelectionComponent implements OnInit {

  category : totalCategory[] = [];
  catList : Category[]; 
  @Output() submit: EventEmitter<number> = new EventEmitter<number>();

  constructor( 
    protected _dataService : DataService,
  ) {
    console.log(this.catList); 
  }

  ngOnInit() {
    console.log(this.catList);
    this.getAllCategory();
  }

  getAllCategory(){
    let body ={
      limit:15,
      offset:0,
      sortingDirection:"DESC",
      sortingField:"modifiedDate"
    }
    this._dataService.getAllCategory("category/getAllParentCategories",body).subscribe(data=>{
      this.catList = data;
      let temp : totalCategory = new totalCategory();
      temp.category = this.catList;
      this.category.push(temp);
    })
  }

  getCategoryById(cat : Category, index : number) : void {
   this._dataService.getAllSubCategoriesOfCategory('category/getAllSubCategoriesOfCategory',cat.categoryId).subscribe(data =>{
    console.log(data);
    let temp : totalCategory = new totalCategory();
    temp.category = data;
    this.modifyTotalCategory(temp,cat,index);
   }); 
  }

  modifyTotalCategory(data : totalCategory, cat : Category, index : number) : void {
    if(cat.lastCategory){
      this.pickedCategory(cat.categoryId)
    } else {
      console.log(index+1,data);
      this.category.splice(index+1, this.category.length);
      this.category.push(data);
    }
  }

  pickedCategory(id:number) : void {
    this.submit.emit(id);
  }
}

export class totalCategory {
  category : Category[]
  constructor() {
    this.category = [];
  }
}
