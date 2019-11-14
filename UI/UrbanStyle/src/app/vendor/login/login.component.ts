import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServiceService } from 'src/_services/user-service.service';


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
    private _userService : UserServiceService
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
      password : this.f.password.value
    }
    this._userService.attemptAuth("",body).subscribe(res=>{
      this.router.navigateByUrl("/vendor/dashboard")
    });
  }

  



}
