import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'addProductHeader',
  templateUrl: './add-product-header.component.html',
  styleUrls: ['./add-product-header.component.scss']
})
export class AddProductHeaderComponent implements OnInit {

  menu = links;

  constructor() { }

  ngOnInit() {
  }

}

const links = [
  {
    name : 'Vital Info',
    url : '/vendor/addProduct/vitalInfo',
    status : true
  },
  {
    name : 'Variation',
    url : '/vendor/addProduct/prodDesc',
    status : false
  },
  // {
  //   name : 'Other',
  //   url : '/vendor/addProduct/prodDesc',
  //   status : false
  // },
  {
    name : 'Image',
    url : '/vendor/addProduct/imageUpload',
    status : false
  },
  {
    name : 'Desc',
    url : '/vendor/addProduct/extraDetails',
    status : false
  },
  {
    name : 'More Details',
    url : '/vendor/addProduct/metaInfo',
    status : false
  },
]
