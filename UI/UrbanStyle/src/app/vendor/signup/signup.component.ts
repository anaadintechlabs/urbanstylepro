import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BasicDetailsComponent } from 'src/app/_forms/basic-details/basic-details.component';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
import { AddAddressComponent } from "src/app/_forms/add-address/add-address.component";

import { MatStepper } from '@angular/material/stepper';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  basicDetails : FormGroup;
  addressDetails : FormGroup;
  bankDetails : FormGroup;
  fromVendorSignUp:boolean=true;
  @ViewChild('stepper',{static:true}) private myStepper: MatStepper;

  @ViewChild('details',{read: ElementRef,static:true}) details : ElementRef;
  constructor(
    private _fb : FormBuilder,
    private _userService : UserServiceService
  ) {
    this.basicDetails = this._fb.group({
      name : ["",Validators.required],
      lastname : [""],
      username : ["",Validators.required],
      email : ["",Validators.required],
      password : ["",Validators.required],
      userType : [""],
    });
   }

  ngOnInit() {
    
  }
  ngAfterViewInit() {
  
  }

   get f() { return this.basicDetails.controls;}

  basicdetialForm(data : FormGroup) : void {
    console.log(data)
    this.basicDetails = data;
    this.f.userType.setValue('VENDOR');
    this.myStepper.next();
  }


   addressForm(data : FormGroup) : void {
    this.addressDetails = data;
   // this.f.userType.setValue('VENDOR');
    // console.log(this.addressDetails);

    this.myStepper.next();
  }


   bankDetailsForm(data : FormGroup) : void {
    this.bankDetails = data;
  
  }

}
