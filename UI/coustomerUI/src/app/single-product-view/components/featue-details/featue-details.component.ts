import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'featue-details',
  templateUrl: './featue-details.component.html',
  styleUrls: ['./featue-details.component.scss']
})
export class FeatueDetailsComponent implements OnInit {

  @Input() feature : any = {};

  constructor() { }

  ngOnInit(): void {
  }

}
