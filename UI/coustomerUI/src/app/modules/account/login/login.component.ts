import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UserService } from 'src/_service/http_&_login/user-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public authForm : FormGroup
  constructor(
    private _fb: FormBuilder,
    private router:Router, 
    private userService:UserService
  ) { 
    this.authForm = this._fb.group({
      'email': ['', Validators.required],
      'password': ['', Validators.required]
      });
  }

  ngOnInit() {
  }

  submitForm() {
    this.userService.attemptAuth(this.authForm.value).subscribe(res=>{
      console.log(res);
    });
  }

}
