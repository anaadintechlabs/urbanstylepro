import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserServiceService } from 'src/_services/http_&_login/user-service.service';
import { User } from 'src/_modals/user.modal';
import { Router } from '@angular/router';

@Component({
  selector: 'vendor-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  @Output() public sidenavToggle = new EventEmitter();
  currentUser : User;
  loginStatus : boolean;
  constructor(
    private _userService : UserServiceService,
    private _router: Router,
  ) { 
    this._userService.currentUser.subscribe(res=>{
      this.currentUser = res;
      console.log(this.currentUser);
      this.setStatus();
    })
    console.log(this.currentUser);
  }

  ngOnInit() {
    let user:string =  this._userService.getUser();
    this.currentUser = JSON.parse(user);
    this.setStatus();
  }

  ngDoCheck(){

  }

  setStatus(){
    if(this.currentUser != undefined && this.currentUser != null){
      if(!Object.keys(this.currentUser).length){
        this.loginStatus = false;
      } else {
        this.loginStatus = true;
      }
    } else {
      this.loginStatus = false; 
    }
  }

  logOut(){
    console.log("worked");
    this._userService.purgeAuth();
    this._router.navigateByUrl("vendor")
  }

  sidvanToggle(){
    this.sidenavToggle.emit();
  }
}
