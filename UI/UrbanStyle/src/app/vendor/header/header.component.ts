import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserServiceService } from 'src/_services/user-service.service';
import { User } from 'src/_modals/user.modal';

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
  ) { 
    this._userService.currentUser.subscribe(res=>{
      this.currentUser = res;
      console.log(this.currentUser);
      if(this.currentUser != undefined && this.currentUser != null){
        if(this.currentUser){
          this.loginStatus = false;
        } else {
          this.loginStatus = true;
        }
      } else {
        this.loginStatus = false; 
      }
    })
    console.log(this.currentUser);
  }

  ngOnInit() {
  }

  sidvanToggle(){
    this.sidenavToggle.emit();
  }
}
