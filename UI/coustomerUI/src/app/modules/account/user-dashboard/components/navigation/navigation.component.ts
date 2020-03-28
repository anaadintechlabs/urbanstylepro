import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/_service/http_&_login/user-service.service';

type Nav = {
  name : string,
  url ?: string,
  icon ?: string,
}

@Component({
  selector: 'dashboard-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {
  public nav : Nav[] = navigations;
  constructor(
    private _userService : UserService
  ) { }

  ngOnInit() {
  }

  logout() {
    this._userService.logout();
  }
}


const navigations: Nav[] = [
  {
    name : 'Profile',
    url : '/classic/account/dashboard/profile',
    icon : 'fas fa-user'
  },
  {
    name : 'Wishlist',
    url : '/classic/account/dashboard/wishlist',
    icon : 'fas fa-heart'
  },
  {
    name : 'Order',
    url : '/classic/account/dashboard/orders',
    icon : 'fas fa-history'
  },
  {
    name : 'Review',
    url : '/classic/account/dashboard/review',
    icon : 'fas fa-comments'
  },
  {
    name : 'Setting',
    url : '/classic/account/dashboard/setting',
    icon : 'fas fa-cogs'
  },
  {
    name : 'Logout',
    url : '/classic/account/dashboard/wishli',
    icon : 'fas fa-sign-out-alt'
  }
]