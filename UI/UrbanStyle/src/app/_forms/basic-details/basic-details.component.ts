import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';

@Component({
  selector: 'basic-details',
  templateUrl: './basic-details.component.html',
  styleUrls: ['./basic-details.component.scss']
})
export class BasicDetailsComponent implements OnInit {

  @Output() basicDetails : EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
  @Input() basicDetailForm : FormGroup;

  submitted : boolean = false;
  
  constructor() { 
  }

  ngOnInit() {
  }

  get f() { return this.basicDetailForm.controls;}

  onSubmit(){
    this.submitted = true;
    if(this.basicDetailForm.invalid){
      
    } else {
      console.log(this.basicDetailForm);
      this.basicDetails.emit(this.basicDetailForm);
    }
  }

}
