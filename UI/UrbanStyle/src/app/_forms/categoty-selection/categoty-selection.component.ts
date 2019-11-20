import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Category } from "../../../_modals/category.modal";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'categoty-selection',
  templateUrl: './categoty-selection.component.html',
  styleUrls: ['./categoty-selection.component.scss']
})
export class CategotySelectionComponent implements OnInit {

  @Input() catList : Category[]; 
  @Output() submit: EventEmitter<number> = new EventEmitter<number>();

  showNavigationArrows = true;
  showNavigationIndicators = true;
  images : any = [1,2,3];
  constructor( private config: NgbCarouselConfig) { 
    config.showNavigationArrows = true;
    config.showNavigationIndicators = true;
  }

  ngOnInit() {
    console.log(this.catList)
  }

  pickedCategory(id:number) : void {
    this.submit.emit(id);
  }

}
