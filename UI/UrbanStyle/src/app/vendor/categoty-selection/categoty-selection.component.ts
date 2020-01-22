import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Category } from "../../../_modals/category.modal";
import { NgbCarouselConfig } from "@ng-bootstrap/ng-bootstrap";
import { DataService } from "src/_services/data/data.service";
import { Router } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: "categoty-selection",
  templateUrl: "./categoty-selection.component.html",
  styleUrls: ["./categoty-selection.component.scss"]
})
export class CategotySelectionComponent implements OnInit {
  catList: Category[];
  @Output() submit: EventEmitter<number> = new EventEmitter<number>();

  constructor(
    protected _dataService: DataService,
    protected _addProductService : AddProductService,
    private _router : Router
  ) {
    console.log(this.catList);
  }

  ngOnInit() {
    console.log(this.catList);
    this.getAllCategory();
  }

  getAllCategory() {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };
    this._dataService
      .getAllCategory("category/getAllParentCategories", body)
      .subscribe(data => {
        this.catList = data;
      });
  }

  getCategoryById(cat: Category, index: number): void {
    if(cat.lastCategory){
      this.pickedCategory(cat.categoryId)
    } else {
      this._dataService
      .getAllSubCategoriesOfCategory(
        "category/getAllSubCategoriesOfCategory",
        cat.categoryId
      )
      .subscribe(data => {
        console.log(data);
        this.catList = data;
      });

    }
  }

  pickedCategory(id: number): void {
    this._addProductService.productFormGroup.get("categoryId").setValue(id);
    this._addProductService.selectedCategory(id);
    this._router.navigateByUrl('/vendor/addProduct/vitalInfo');
  }
}


