import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'basic-details',
  templateUrl: './basic-details.component.html',
  styleUrls: ['./basic-details.component.scss']
})
export class BasicDetailsComponent implements OnInit {

  @Output() basicDetails : EventEmitter<FormGroup> = new EventEmitter<FormGroup>();

  basicDetailForm : FormGroup;
  submitted : boolean = false;
  
  constructor(private _fb : FormBuilder) { 
    this.basicDetailForm = this._fb.group({
      name : ["",Validators.required],
      lastName : [""],
      userName : ["",Validators.required],
      email : ["",Validators.required],
      password : ["",Validators.required],
    })
  }

  ngOnInit() {
  }

  get f() { return this.basicDetailForm.controls;}

  onSubmit(){
    console.log("called");
    this.submitted = true;
    if(this.basicDetailForm.invalid){

    } else {
      console.log(this.basicDetailForm);
      this.basicDetails.emit(this.basicDetailForm);
    }
  }

}
