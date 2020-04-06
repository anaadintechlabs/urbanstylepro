import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'review-card',
  templateUrl: './review-card.component.html',
  styleUrls: ['./review-card.component.scss']
})
export class ReviewCardComponent implements OnInit {

  @Input() review : any = {};

  constructor() { }

  ngOnInit(): void {
  }

}
