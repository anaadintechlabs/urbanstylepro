import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/_services/data.service';
import { Category } from 'src/_modals/category.modal';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  vitalInfo : FormGroup;
  constructor(protected _dataService : DataService) { }

  ngOnInit() {
 
  }

 
  selectedCategory(catId:number){
    console.log(catId);
  }

}
