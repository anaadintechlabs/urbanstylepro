import { JwtServiceService } from './jwt-service.service';
import { Observable } from 'rxjs/internal/Observable';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { distinctUntilChanged } from 'rxjs/internal/operators/distinctUntilChanged';
import { ReplaySubject } from 'rxjs/internal/ReplaySubject';
import { ApiService } from './api.service';
import { Router } from "@angular/router";
import { User } from 'src/_modals/user';
import { HttpHeaders, HttpClient, HttpBackend } from '../../../node_modules/@angular/common/http';
import { Address,Country, State, City } from "../../_modals/address";
import { HttpParams } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public redirectUrl: string;

  userUrl='http://localhost:8081/urban/'

  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(0);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();
  constructor(
    private jwtService:JwtServiceService,
    private apiService:ApiService, 
    private router: Router,private httpBackend:HttpBackend,
    private httpClient:HttpClient,
    private jwtServiceService:JwtServiceService
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

  // setAuth(user) {
  //   console.log(user.accessToken);
  //   // Save JWT sent from server in localstorage
  //   this.jwtService.saveToken(user.accessToken);
  //   // Set current user data into observable
  //   this.currentUserSubject.next(user.accessToken);
  //   // Set isAuthenticated to true
  //   this.isAuthenticatedSubject.next(true);
  // }

  logout(){
    console.log('logout');
    this.purgeAuth();
    
     this.router.navigate(['/classic/home']);
//
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


  getAllReviewsOfUser(filter)
  {
    let currunt_user = JSON.parse(this.getUser());
    const route ='review/getAllReviewsOfUser?userId='+currunt_user.id;
    return this.apiService.post( route, filter).pipe(
      map(data => {
        return data;
      }
    ));
  }

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

  softDeleteProductReview(id)
  {
    const route ='review/softDeleteProductReview?reviewId='+id;
    return this.apiService.delete( route).pipe(
      map(data => {
        return data.data;
      }
    ));
  }

  getAddressDetailsByUser()
  {
    let currunt_user = JSON.parse(this.getUser());
    const route ='api/getAddressDetailsByUser?userId='+currunt_user.id;
    return this.apiService.getUser( route).pipe(
      map(data => {
        return data.data;
      }
    ));
  }

  deleteAddressDetails(addressId)
  {
    let currunt_user = JSON.parse(this.getUser());
    let url =
      "api/deleteAddressDetails?userId=" +
      currunt_user.id +
      "&addressId=" +
      addressId +
      "&status=0";
    return this.apiService.deleteUser( url).pipe(
      map(data => {
        return data.data;
      }
    ));
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
         'Authorization': 'Bearer ' + this.jwtServiceService.getToken()
       }),
     }
 
     this.httpClient= new HttpClient(this.httpBackend);
          let url=this.userUrl+'api/user/updateUser';
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




    //address apis
     getAllCountries(url: string): Observable<Country[]> {
    return new Observable<Country[]>(obs => {
      this.apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.countryList);
        }
      });
    });
  }
  
  getAllStatesOfCountry(url: string): Observable<State[]> {
    return new Observable<State[]>(obs => {
      this.apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.stateList);
        }
      });
    });
  }

  getAllCityOfState(url: string): Observable<City[]> {
    return new Observable<City[]>(obs => {
      this.apiService.postUser(url).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.cityList);
        }
      });
    });
  }


   getAddressInformation(url: string, addressId): Observable<Address> {
    const param: HttpParams = new HttpParams().set("addressId", addressId);

    return new Observable<Address>(obs => {
      this.apiService.getUser(url, param).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.address);
        }
      });
    });
  }

   saveAddressDetails(url, data): Observable<Address> {
    return new Observable<any>(obs => {
      this.apiService.postUser(url, data).subscribe(res => {
        if (res.isSuccess) {
          obs.next(res.data.addressDetails);
        }
      });
    });
  }

}
