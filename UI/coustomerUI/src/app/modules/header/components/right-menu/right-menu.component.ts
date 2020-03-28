import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { UserService } from 'src/_service/http_&_login/user-service.service';

@Component({
  selector: 'right-menu',
  templateUrl: './right-menu.component.html',
  styleUrls: ['./right-menu.component.scss']
})
export class RightMenuComponent implements OnInit {

  isLoggedIn : boolean;
  searchEnable : boolean = false;
  @Output() showSearch : EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(
    public _userService : UserService
  ) {
    this._userService.isAuthenticated.subscribe(status=>{
      console.log('status',status);
      this.isLoggedIn = status;
    })
   }

  ngOnInit() {
  }

  searchClicked() {
    console.log('srch clicked');
    this.searchEnable = !this.searchEnable;
    this.showSearch.emit(this.searchEnable);
    console.log('srch shoot');
  }

}
