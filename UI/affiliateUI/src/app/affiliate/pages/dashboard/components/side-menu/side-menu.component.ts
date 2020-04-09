import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/_services/http_&_login/user-service.service';
type route = {
  name : string,
  route : string,
}

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnInit {
  allRoute : route[] = [];
  constructor(
    private userService : UserService
  ) { }

  ngOnInit() {
    this.allRoute = route;
  }

  logout() {
    this.userService.logout();
  }

}

const route : route[] = [
  {
    name : 'All product',
    route : '/affiliate/dashboard/products'
  },
  {
    name : 'Orders',
    route : '/affiliate/dashboard/shared'
  },
  {
    name : 'Returns',
    route : '/affiliate/dashboard/history'
  },
  {
    name : 'Account Details',
    route : '/affiliate/dashboard/accDetails'
  },
  {
    name : 'Logout',
    route : 'Logout'
  },
]
