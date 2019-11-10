import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BasicDetailsComponent } from 'src/app/_forms/basic-details/basic-details.component';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  basicDetails : FormGroup;
  @ViewChild('details',{read: ElementRef,static:true}) details : ElementRef;
  constructor() { }

  ngOnInit() {
    
  }
  ngAfterViewInit() {
    console.log('Values on ngAfterViewInit():');
    console.log("sample:", this.details.nativeElement);
  }

  basicdetialForm(data : FormGroup) : void {
    this.basicDetails = data;
    console.log(this.basicDetails);
  }

}
