import { Component, OnInit, NgZone } from '@angular/core';
import { AddProductService } from 'src/_services/product/addProductService';

@Component({
  selector: 'app-extra-details',
  templateUrl: './extra-details.component.html',
  styleUrls: ['./extra-details.component.scss']
})
export class ExtraDetailsComponent implements OnInit {
  features : string[];

  constructor(
    public _addProductService : AddProductService,
    public _zone : NgZone
  ) { }

  ngOnInit() {
    this._zone.runOutsideAngular(()=>{
      this.features = [""];
      let temp = this._addProductService.productFormGroup.get('features');
      temp.patchValue(this.features.toString());
    })
    console.log(this._addProductService.productDTO.value);
  }

  get f() {
    return this._addProductService.productDescFormGroup.controls;
  }

  addMoreFeature() {
    this._zone.runOutsideAngular(()=>{
      this._addProductService.features.push("");
      console.log(this.features);
    })
  }

  removeFeature(index) {
    this._addProductService.features.splice(index,1)
    console.log(this.features);
  }

}
