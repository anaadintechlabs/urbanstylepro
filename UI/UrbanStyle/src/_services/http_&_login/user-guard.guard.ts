import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { CanActivate } from "@angular/router";
import { Router } from "@angular/router";
import { UserServiceService } from "./user-service.service";

@Injectable({
  providedIn: 'root'
})
export class UserGuardGuard implements  CanActivate {

  loginStatus:boolean;
 public user:any;

  constructor(
    private router: Router,
    private _userService: UserServiceService
  ) { 

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    this.user=this._userService.getUser();
     if(this.user!=undefined){
      if(!Object.keys(this.user).length){
          this.router.navigateByUrl("");
        } else {
            this.user= JSON.parse(this._userService.getUser())
            
            if(this.user.userType == 'USER') {
              this.router.navigateByUrl("/home");
            } else if (this.user.userType == 'VENDOR'){

              // this.router.navigateByUrl("/vendor/dashboard")
              return true;
            }
        }
     }
    else {
       this.router.navigateByUrl("/vendor/login");
    }
    //
    return true;
  }

}
