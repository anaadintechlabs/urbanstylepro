import { Component, OnInit } from '@angular/core';
import { EditProductService } from '../../service/editProduct.service';

@Component({
  selector: 'edit-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  menu = []

  constructor(
    public _editProduct :EditProductService
  ) { 
    this.menu =  [
      {
        name : 'Vital Info',
        url : `/edit/${this._editProduct.produtId}/${this._editProduct.variantId}/vitalInfo`,
        status : true
      },
      {
        name : 'Variation',
        url : `/edit/${this._editProduct.produtId}/${this._editProduct.variantId}/prodDesc`,
        status : false
      },
      {
        name : 'Image',
        url : `/edit/${this._editProduct.produtId}/${this._editProduct.variantId}/imageUpload`,
        status : false
      },
      {
        name : 'Desc',
        url : `/edit/${this._editProduct.produtId}/${this._editProduct.variantId}/extraDetails`,
        status : false
      },
      {
        name : 'More Details',
        url : `/edit/${this._editProduct.produtId}/${this._editProduct.variantId}/metaInfo`,
        status : false
      },
    ];
  }

  ngOnInit() {
  }

}

const links = [
  {
    name : 'Vital Info',
    url : '/edit/vitalInfo',
    status : true
  },
  {
    name : 'Variation',
    url : '/edit/prodDesc',
    status : false
  },
  {
    name : 'Image',
    url : '/edit/imageUpload',
    status : false
  },
  {
    name : 'Desc',
    url : '/edit/extraDetails',
    status : false
  },
  {
    name : 'More Details',
    url : '/edit/metaInfo',
    status : false
  },
]