
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
    this.basicDetails = this._fb.group({
      name: ["", Validators.required],
      lastname: [""],
      username: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      userType: ["VENDOR"]
    });

    this.addressDetails = new FormGroup({
      id: new FormControl(""),
      addressOne: new FormControl("", [
        Validators.required,
        Validators.minLength(4)
      ]),
      addressTwo: new FormControl("", [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(255)
      ]),
      status: new FormControl(1),
      zip: new FormControl("", [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6)
      ]),
      country: new FormGroup({
        id: new FormControl("")
      }),
      state: new FormGroup({
        id: new FormControl("")
      }),
      cite: new FormGroup({
        id: new FormControl("")
      }),
      user: new FormGroup({
        id: new FormControl("")
      })
    });

    this.bankDetails = new FormGroup({
      id: new FormControl(""),
      bankName: new FormControl("", [
        Validators.required,
        Validators.minLength(3)
      ]),
      accountNumber: new FormControl("", [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(255)
      ]),
      status: new FormControl(1),
      accType: new FormControl("", [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(10)
      ]),
      ifscCode: new FormControl("", [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(15)
      ]),
  
      user: new FormGroup({
        id: new FormControl("")
      })
    });

  }

  ngOnInit() {}
  ngAfterViewInit() {}

  get f() {
    return this.basicDetails.controls;
  }

  next(form : FormGroup){
    if(form.status == 'VALID'){
      this.myStepper.next();
    }
  }

  save(){
    this.signUpform = this._fb.group({
      signUp: this.basicDetails,
      address: this.addressDetails,
      bankDetails: this.bankDetails
    });
    console.log(this.signUpform);
    this._userService.attempIntegratedAuth(this.signUpform.value).subscribe(
      data => {
        this._router.navigateByUrl('/account/login');
      },
      error => {
        console.log("error======", error);
      }
    );
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
