import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';

@Component({
  selector: 'basic-details',
  templateUrl: './basic-details.component.html',
  styleUrls: ['./basic-details.component.scss']
})
export class BasicDetailsComponent implements OnInit {

  @Input() basicDetailForm : FormGroup;

  submitted : boolean = false;
  
  constructor() { 
  }

  ngOnInit() {
  }

  get f() { return this.basicDetailForm.controls;}


}
