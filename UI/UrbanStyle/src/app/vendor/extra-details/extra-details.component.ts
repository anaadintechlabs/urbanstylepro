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
      this._addProductService.features = [""];
      let temp = this._addProductService.productFormGroup.get('features');
      temp.patchValue(this._addProductService.features.toString());
    })
    console.log(this._addProductService.productDTO.value);
  }

  get f() {
    return this._addProductService.productDescFormGroup.controls;
  }

  addMoreFeature() {
    this._addProductService.features.push("");
  }

  removeFeature(index) {
    this._addProductService.features.splice(index,1)
    console.log(this._addProductService.features);
  }

  onChange(item,i) {
    // console.log('work');
    this._addProductService.features[i] = item;
    this._addProductService.productFormGroup.get('features').patchValue(JSON.stringify(this._addProductService.features));
    // console.log(this._addProductService.features);
  }

}
