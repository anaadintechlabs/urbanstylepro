import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'prod-desc',
  templateUrl: './prod-desc.component.html',
  styleUrls: ['./prod-desc.component.scss']
})
export class ProdDescComponent implements OnInit {

  @Input() desc : any = {};

  constructor() { }

  ngOnInit(): void {
  }

}
