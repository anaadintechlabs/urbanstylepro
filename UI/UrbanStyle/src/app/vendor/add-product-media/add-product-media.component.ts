import { Component, OnInit, EventEmitter, Output, Input } from "@angular/core";
import { AddProductService } from 'src/_services/product/addProductService';
import { Router } from '@angular/router';

@Component({
  selector: "product-media",
  templateUrl: "./add-product-media.component.html",
  styleUrls: ["./add-product-media.component.scss"]
})
export class AddProductMediaComponent implements OnInit {


  constructor(
    private _addProduct : AddProductService,
    private _router:Router
  ) {}

  ngOnInit() {}

 

}
