import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'actionHandler',
  templateUrl: './action-handler.component.html',
  styleUrls: ['./action-handler.component.scss']
})
export class ActionHandlerComponent implements OnInit {

  @Input() options : string[];
  @Output() selectedValue : EventEmitter<string> = new EventEmitter<string>();

  tempValue : string = "";

  constructor() { }

  ngOnInit() {
  }

  submit() {
    if(this.tempValue) {
      this.selectedValue.emit(this.tempValue);
    }
  }

}
