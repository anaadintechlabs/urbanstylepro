import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'vendor-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  @Output() public sidenavToggle = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  sidvanToggle(){
    this.sidenavToggle.emit();
  }
}
