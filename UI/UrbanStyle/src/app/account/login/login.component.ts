import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
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
    private _userService : UserServiceService,
    private toastr: ToastrService
    ) {

     }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() { return this.loginForm.controls; }

  //USER TYPE HARD CODED
  onSubmit(loginForm : FormGroup) {
    this.loginForm = loginForm;
    let body = {
      email : this.f.email.value,
      password : this.f.password.value,
      userType:'VENDOR'
    }
    this._userService.attemptAuth("",body).subscribe(res=>{
      this.toastr.success('Login Successfull', 'Success');
      this.router.navigateByUrl("/vendor/dashboard")
    },error=>{
      this.toastr.error("Please Enter valid Credentials!","Oops!")
      //alert("Please Enter valid Credentials");
    });
  }

  



}
