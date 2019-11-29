import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BasicDetailsComponent } from 'src/app/_forms/basic-details/basic-details.component';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
import { AddAddressComponent } from "src/app/_forms/add-address/add-address.component";


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  basicDetails : FormGroup;
  addressDetails : FormGroup;
  bankDetails : FormGroup;

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
    this.basicDetails = data;
    this.f.userType.setValue('VENDOR');
    console.log(this.basicDetails);
    this._userService.attempiSignUp(this.basicDetails.value).subscribe(res=>{
      console.log(res);
    });
  }

}
