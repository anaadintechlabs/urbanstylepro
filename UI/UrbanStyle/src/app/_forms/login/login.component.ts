import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @Output() onLogin : EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
  loginForm : FormGroup;
  submitted = false; 
  error = '';

  constructor(
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
        });
    // get return url from route parameters or default to '/'
   
  }

  get f() {return this.loginForm.controls}

  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.loginForm.invalid) {
        return;
    } else {
      console.log("form",this.loginForm);
      this.onLogin.emit(this.loginForm);
    }
  }

}
