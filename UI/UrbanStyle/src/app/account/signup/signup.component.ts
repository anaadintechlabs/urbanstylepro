
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { BasicDetailsComponent } from 'src/app/vendor/basic-details/basic-details.component';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
import { AddAddressComponent } from "src/app/_forms/add-address/add-address.component";

import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';

@Component({
  selector: "app-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"]
})
export class SignupComponent implements OnInit {

  signUpform :  FormGroup;
  basicDetails : FormGroup;
  addressDetails : FormGroup;
  bankDetails : FormGroup;
  fromVendorSignUp:boolean=true;
  @ViewChild('stepper',{static:true}) private myStepper: MatStepper;

  @ViewChild('details',{read: ElementRef,static:true}) details : ElementRef;
  constructor(
    private _fb : FormBuilder,
    private _userService : UserServiceService,
    private _router : Router
  ) {
   this.signUpform = this._fb.group({
    signUp : new FormControl,
    address : new FormControl,
    bankDetails : new FormControl
   }) 
    this.basicDetails = this._fb.group({
      name: ["", Validators.required],
      lastname: [""],
      username: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      userType: [""]
    });

    this.signUpform = this._fb.group({
      signUp: this.basicDetails,
      address: this.addressDetails,
      bankDetails: this.bankDetails
    });
  }

  ngOnInit() {}
  ngAfterViewInit() {}

  get f() {
    return this.basicDetails.controls;
  }

  basicdetialForm(data: FormGroup): void {
    this.basicDetails = data;
    this.f.userType.setValue("VENDOR");
    this.myStepper.next();
  }

  addressForm(data: FormGroup): void {
    this.addressDetails = data;
    this.signUpform.controls.address.patchValue(this.addressDetails);
     console.log("save", this.signUpform.value);
    this.myStepper.next();
  }



   bankDetailsForm(data : FormGroup) : void {
    this.bankDetails = data;
    console.log(this.bankDetails);
    this.signUpform.controls.signUp.patchValue(this.basicDetails.value);
    this.signUpform.controls.address.patchValue(this.addressDetails.value);
    this.signUpform.controls.bankDetails.patchValue(this.bankDetails.value);
    console.log(this.signUpform);
    this._userService.attempIntegratedAuth(this.signUpform.value).subscribe(
      data => {
        this._router.navigateByUrl('/vendor/login');
      },
      error => {
        console.log("error======", error);
      }
    );
  }
}
