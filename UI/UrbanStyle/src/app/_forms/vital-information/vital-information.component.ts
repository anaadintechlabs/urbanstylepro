import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'vital-information',
  templateUrl: './vital-information.component.html',
  styleUrls: ['./vital-information.component.scss']
})
export class VitalInformationComponent implements OnInit {

  @Input() vitalInfo : FormGroup;

  constructor() { }

  ngOnInit() {
  }

}
