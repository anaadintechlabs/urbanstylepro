import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/_services/data.service';
import { Category } from 'src/_modals/category.modal';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  category : Category[]=[];
  constructor(protected _dataService : DataService) { }

  ngOnInit() {
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
      this.category = data;
      console.log("hiiii",this.category);
    })
  }

}
