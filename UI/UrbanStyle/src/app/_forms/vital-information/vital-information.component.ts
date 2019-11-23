import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: 'vital-information',
  templateUrl: './vital-information.component.html',
  styleUrls: ['./vital-information.component.scss']
})
export class VitalInformationComponent implements OnInit {

  @Input() vitalInfo : FormGroup;
  @Output() status : EventEmitter<boolean> = new EventEmitter<boolean>();

  private _productService : AddProductService;
  constructor() {
    
  }

  get f() {
    return this.vitalInfo.controls;
  }

  ngOnInit() {
    // console.log(this.vitalInfo);
  }

  ngDoCheck() {
    if(this.vitalInfo.status == 'INVALID') {
      this.status.emit(false);
    } else {
      this.status.emit(true);
    }
  }

}
