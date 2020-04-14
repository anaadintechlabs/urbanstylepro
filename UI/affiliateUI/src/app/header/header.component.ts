import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/_services/http_&_login/user-service.service';
type route = {
  name : string,
  icon: string;
  route : string,
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  links : route[] = [];
  constructor(
    private userService : UserService
  ) { }

  ngOnInit() {
    this.links = route;
  }

  logout() {
    this.userService.logout();
  }

}

const route: route[] = [
  {
    name : 'Edit Profile',
    icon : "fas fa-id-card",
    route : '/affiliate/dashboard/profile'
  },
  {
    name : 'Settings',
    icon : "fas fa-cogs",
    route : '/affiliate/dashboard/setting'
  },

]
