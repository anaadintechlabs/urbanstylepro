import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/_services/http_&_login/user-service.service';
type route = {
  name : string,
  icon: string;
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
    icon : "fab fa-product-hunt",
    route : '/affiliate/dashboard/products'
  },
  {
    name : 'Orders',
    icon : "fas fa-folder-open",
    route : '/affiliate/dashboard/shared'
  },
  {
    name : 'Returns',
    icon : "fas fa-exchange-alt",
    route : '/affiliate/dashboard/history'
  },
  {
    name : 'Account Details',
    icon : "fas fa-user-circle",
    route : '/affiliate/dashboard/accDetails'
  },
  {
    name : 'Statistics',
    icon : "fas fa-chart-pie",
    route : '/affiliate/dashboard/stats'
  },
  // {
  //   name : 'Edit Profile',
  //   icon : "fas fa-id-card",
  //   route : '/affiliate/dashboard/profile'
  // },
  // {
  //   name : 'Settings',
  //   icon : "fas fa-cogs",
  //   route : '/affiliate/dashboard/setting'
  // },
  // {
  //   name : 'Logout',
  //   icon : "fas fa-sign-out-alt",
  //   route : 'Logout'
  // },
]
