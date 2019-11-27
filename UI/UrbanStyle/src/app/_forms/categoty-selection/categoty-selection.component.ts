import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Category } from "../../../_modals/category.modal";

@Component({
  selector: 'categoty-selection',
  templateUrl: './categoty-selection.component.html',
  styleUrls: ['./categoty-selection.component.scss']
})
export class CategotySelectionComponent implements OnInit {

  @Input() catList : Category[]; 
  @Output() submit: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit() {
    console.log(this.catList)
  }

  pickedCategory(id:number) : void {
    this.submit.emit(id);
  }

}
