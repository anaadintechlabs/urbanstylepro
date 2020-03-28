import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { ApiService } from 'src/_service/http_&_login/api.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  public signUpForm : FormGroup;
  constructor(
    public _fb : FormBuilder,
    public _userService : UserService,
    private _apiService  :ApiService
  ) { }

  ngOnInit() {
    this.signUpForm = this._fb.group({
      name : new FormControl('',[Validators.required,Validators.minLength(3),Validators.maxLength(30)]),
      username : new FormControl('',[Validators.required,Validators.minLength(3),Validators.maxLength(30)]),
      email : new FormControl('',[Validators.required,Validators.minLength(3),Validators.maxLength(30)]),
      password  : new FormControl('',[Validators.required,Validators.minLength(6),Validators.maxLength(18)]),
      userType  : new FormControl('CUSTOMER',[Validators.required,Validators.minLength(3),Validators.maxLength(30)]),
    })
  }

  signup() {
    console.log(this.signUpForm.status);
    if(this.signUpForm.status == 'VALID') {
      console.log("work");
      // this._userService.attempiSignUp(this.signUpForm.value);
      this._apiService.postUser('api/auth/signup',this.signUpForm.value).subscribe(res => {
        console.log(res);
      })
    }
  }

}
