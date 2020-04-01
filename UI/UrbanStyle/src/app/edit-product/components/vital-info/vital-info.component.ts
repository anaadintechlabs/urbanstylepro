import { Component, OnInit } from '@angular/core';
import { EditProductService } from '../../service/editProduct.service';

@Component({
  selector: 'app-vital-info',
  templateUrl: './vital-info.component.html',
  styleUrls: ['./vital-info.component.scss']
})
export class VitalInfoComponent implements OnInit {

  constructor(
    private _editProduct : EditProductService
  ) { }

  ngOnInit() {
  }

}
