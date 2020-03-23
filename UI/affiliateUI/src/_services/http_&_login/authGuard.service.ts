import { Injectable } from "@angular/core";
import {
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from "@angular/router";
import { CanActivateChild, CanActivate } from "@angular/router";
import { UserService } from "./user-service.service";

@Injectable({
  providedIn: "root"
})
export class AuthGuardService implements CanActivate {
  loginStatus: boolean;
  public user: any;

  constructor(
      private router: Router, 
      private userService: UserService
    ) {
    // this.data.currentStatus.subscribe(login => this.loginStatus = login);
    // this.user = JSON.parse(this.userService.getUser());
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      if(this.userService.getUser()) {
        this.user = JSON.parse(this.userService.getUser());
      } else {
        this.user = undefined;
      }
    return this.checkLogin(state.url);
  }

  checkLogin(url: string): boolean {
      console.log(this.user);
      if(this.user){
          if(Object.keys(this.user).length){
              return true;
          } else {
              this.userService.redirectUrl = url;
              this.router.navigateByUrl('/account');
              return true;
          }
      } else {
        this.userService.redirectUrl = url;
        this.router.navigateByUrl('/account');
        return true;
      }
  }
}
