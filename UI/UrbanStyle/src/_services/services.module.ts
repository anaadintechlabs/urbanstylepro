import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './http_&_login/api.service';
import { AuthGuardService } from './http_&_login/authGuard.service';
import { DataService } from './data/data.service';
import { HttpTokenInterceptorService } from './http_&_login/http-token-interceptor.service';
import { JwtServiceService } from './http_&_login/jwt-service.service';
import { UserGuardGuard } from './http_&_login/user-guard.guard';
import { UserServiceService } from './http_&_login/user-service.service';
import { AddProductService } from './product/addProductService';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
  ],
  providers:[
    ApiService,
    AuthGuardService,
    DataService,
    HttpTokenInterceptorService,
    JwtServiceService,
    UserGuardGuard,
    UserServiceService,
    AddProductService
] 
})
export class ServicesModule { }
