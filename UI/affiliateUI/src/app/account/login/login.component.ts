import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/_services/http_&_login/user-service.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  returnUrl: string;
  loginForm : FormGroup;
  
  constructor( 
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private _userService : UserService,
    private toastr: ToastrService
    ) {

    }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() { return this.loginForm.controls; }

  onSubmit(loginForm : FormGroup) {
    this.loginForm = loginForm;
    let body = {
      email : this.f.email.value,
      password : this.f.password.value,
      userType:'AFFILIATE'
    }
    this._userService.attemptAuth(body).subscribe(res=>{
      this.toastr.success('Login Successfull', 'Success');
    },error=>{
      this.toastr.error("Please Enter valid Credentials!","Oops!")
    });
  }

  



}
