import { Component, OnInit, Input } from '@angular/core';
import { Category } from "../../../_modals/category.modal";

@Component({
  selector: 'app-categoty-selection',
  templateUrl: './categoty-selection.component.html',
  styleUrls: ['./categoty-selection.component.scss']
})
export class CategotySelectionComponent implements OnInit {

  @Input() catList : Category[]; 

  constructor() { }

  ngOnInit() {
  }

}
