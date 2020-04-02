import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy } from "@angular/core";
import { Category } from "../../../_modals/category.modal";
import { DataService } from "src/_services/data/data.service";
import { Router } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: "categoty-selection",
  templateUrl: "./categoty-selection.component.html",
  styleUrls: ["./categoty-selection.component.scss"],
})
export class CategotySelectionComponent implements OnInit {
  catListData: any;

  constructor(
    protected _dataService: DataService,
    protected _addProductService : AddProductService,
    private _router : Router,
  ) {
    // console.log(this.catList);
    this._addProductService.changeHeaderStaus(false);
    this.getAllCategory();
  }

  ngOnInit() {
    // console.log(this.catList);
  
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
        this.catListData = data;
        console.log(this.catListData);
      });
  }

  getCategoryById(cat: Category): void {
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
        this.catListData = data;
      });

    }
  }

  pickedCategory(id: number): void {
    this._addProductService.selectedCatID = id;
    this._addProductService.productStatus = 'ADD';
    this._addProductService.selectedCategory(id);
    this._addProductService.productFormGroup.get("categoryId").setValue(id);
    this._addProductService.changeHeaderStaus(true);
    this._router.navigateByUrl('/vendor/addProduct/vitalInfo');
  }
}


