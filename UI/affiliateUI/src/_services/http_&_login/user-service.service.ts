import { JwtServiceService } from './jwt-service.service';
import { Observable } from 'rxjs/internal/Observable';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { distinctUntilChanged } from 'rxjs/internal/operators/distinctUntilChanged';
import { ReplaySubject } from 'rxjs/internal/ReplaySubject';
import { ApiService } from './api.service';
import { Router } from "@angular/router";
import { User } from 'src/_modals/user.modal';
import { HttpHeaders, HttpClient, HttpBackend } from "@angular/common/http";
import { environment } from "src/environments/environment";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  public redirectUrl: string;

  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(0);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();
  constructor(
    private jwtService:JwtServiceService,
    private apiService:ApiService, 
    private router: Router,
    private httpBackend:HttpBackend,
     private httpClient:HttpClient,
  ) {
    this.load();
  }

  load(){
    if(this.getUser() && this.getUser().length){
      let user : User = JSON.parse(this.getUser());
      if(Object.keys(user).length){
        this.currentUserSubject.next(user);
        this.isAuthenticatedSubject.next(true);
      } else {
        this.currentUserSubject.next({} as User);
        this.isAuthenticatedSubject.next(false);
      }
    }
  }

  populate() {
    // If JWT detected, attempt to get & store user's info
    if (this.jwtService.getToken()) {
      this.apiService.get('/user')
      .subscribe(
        data => this.setAuth(data.user),
        err => this.purgeAuth()
      );
    } else {
      // Remove any potential remnants of previous auth states
      this.purgeAuth();
    }
  }

  // Response we are getting is accessToken and tokenType which are not presesnt in user class

  setAuth(user: User) {

    // Save JWT sent from server in localstorage
    this.jwtService.saveToken(user.token);
    // Set current user data into observable
    this.currentUserSubject.next(user);
    // Set isAuthenticated to true
    this.isAuthenticatedSubject.next(true);

    this.saveUser(user);
  }


  logout(){
    console.log('logout');
    this.purgeAuth();
    this.router.navigate(['']);
  }
    
  purgeAuth() {
    // Remove JWT from localstorage
    this.jwtService.destroyToken();
    // Set current user to an empty object
    this.currentUserSubject.next({} as User);
    // Set auth status to false
    this.isAuthenticatedSubject.next(false);
    this.destroyUser();
  }

  attemptAuth(credentials): Observable<User>
  {
      const route ='/login';
      return this.apiService.postUser('api/auth' + route, credentials).pipe(
        map(data => {
          this.setAuth(data);
          this.navigateToDashboardBasedOnUserType(data.userType);
          this.router.navigateByUrl(this.redirectUrl);
          return data;
        }
      ));
  }

  attempiSignUp(credentials){
    const route ='/signup';
    return this.apiService.postUser('api/auth' + route, credentials).pipe(
      map(data => {
        console.log("data is",data)
        return data;
      }
    ));
  }



  navigateToDashboardBasedOnUserType(userType)
  {
    console.log(userType);
    if(userType == 'VENDOR')
      {
        this.router.navigateByUrl("vendor/dashboard");
      }
  }
  getCurrentUser(): User {
    return this.currentUserSubject.value;
  }

  getUser(): string {
    return window.localStorage['user'];
  }

  destroyUser(){
    window.localStorage.removeItem('user');
  }

  saveUser(user){
    window.localStorage['user'] = JSON.stringify(user);
  }

  // Update the user on the server (email, pass, etc)
  update(user): Observable<User> {
    return this.apiService
    .put('/user', { user })
    .pipe(map(data => {
      // Update the currentUser observable
      this.currentUserSubject.next(data['user']);
      return data['user'];
    }));
  }

  attempIntegratedAuth(data){
    console.log(data);
    const route ='/vendorSignUpIntegrated';
    return this.apiService.postUser('api/vendor' + route, data).pipe(
      map(data => {
        return data;
      }
    ));
  }

  //Change these servicesto order

    getWalletDetailsOfUser(filter)
  {
     let currunt_user = JSON.parse(this.getUser());
    const route ='api/getWalletByUser?userId='+currunt_user.id;
    return this.apiService.postOrder( route, filter).pipe(
      map(data => {
        return data;
      }
    ));
  }
  
   getOrderOfUser(filter)
    {
        console.log("filter is",filter)
        if(filter)
        {
        let currunt_user = JSON.parse(this.getUser());
        let url='api/getOrderByAffiliate?affiliateId='+currunt_user.id;
        return new Observable<any>(obs => {
            this.apiService.postOrder(url,filter).subscribe(res=>{
                return obs.next(res);
              
          });
      });  
    }
    }

    getAllReturnOfAffiliate(affiliateId,filter)
    {
      if(filter)
        {
        let currunt_user = JSON.parse(this.getUser());
        let url='api/getReturnByAffiliate?affiliateId='+currunt_user.id;
        return new Observable<any>(obs => {
            this.apiService.postOrder(url,filter).subscribe(res=>{
                return obs.next(res);
              
          });
      });  
         }

    }



    
  changePassword(obj)
  {
    let currunt_user = JSON.parse(this.getUser());
    let url =
      "api/user/changePassword?userId=" +
      currunt_user.id ;
    return this.apiService.postUser(url,obj).pipe(
      map(data => {
        return data;
      }
    ));
  }


  getLoggerInUserDetails()
  {
    let currunt_user = JSON.parse(this.getUser());
    let url =
      "api/user/getUserById?userId=" +
      currunt_user.id ;
    return this.apiService.getUser(url).pipe(
      map(data => {
        return data.data;
      }
    ));
  }

  updateUser(data)
  {
        const HttpUploadOptions = {
       headers: new HttpHeaders({
         'Authorization': 'Bearer ' + this.jwtService.getToken()
       }),
     }
 
     this.httpClient= new HttpClient(this.httpBackend);
          let url=environment.user_url+'api/user/updateUser';
        return this.httpClient.put(url,data,HttpUploadOptions).pipe(map(this.successResponse), catchError(this.errorHandler));
  }

  
  errorHandler(error){
    return Observable.throw(error) ;
    }
  
    successResponse(response){
    try {
        if (response) {
          return response.data;
        }
      }
      catch (ex) {
        console.log(ex);
      }
      return response;
  
    }

    getTotalComissionGroupByProduct(filter)
    {
        if(filter)
        {
        let currunt_user = JSON.parse(this.getUser());
        let url='api/getTotalComissionGroupByProduct?affiliateId='+currunt_user.id;
        return new Observable<any>(obs => {
            this.apiService.postOrder(url,filter).subscribe(res=>{
                return obs.next(res);
              
          });
      });  
    }
    }
}
