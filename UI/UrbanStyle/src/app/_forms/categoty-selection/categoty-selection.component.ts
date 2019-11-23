import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Category } from "../../../_modals/category.modal";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';
import { DataService } from "src/_services/data.service";

@Component({
  selector: 'categoty-selection',
  templateUrl: './categoty-selection.component.html',
  styleUrls: ['./categoty-selection.component.scss']
})
export class CategotySelectionComponent implements OnInit {

  @Input() catList : Category[]; 
  @Output() submit: EventEmitter<number> = new EventEmitter<number>();

  constructor(protected  _dataService:DataService ) { 
  }

  ngOnInit() {
    // console.log(this.catList)
    this.getAllCategory();
    
  }

  pickedCategory(id:number) : void {
    this.submit.emit(id);
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
      console.log("hiiii",this.catList);
    })
  }

    getCategoryById(id : number) : void {
   this._dataService.getAllSubCategoriesOfCategory('category/getAllSubCategoriesOfCategory',id).subscribe(data =>{
    console.log(data);
   });
  }

}
