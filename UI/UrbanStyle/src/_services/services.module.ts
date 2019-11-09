import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';
import { AuthGuardService } from './authGuard.service';
import { DataService } from './data.service';
import { HttpTokenInterceptorService } from './http-token-interceptor.service';
import { JwtServiceService } from './jwt-service.service';
import { UserGuardGuard } from './user-guard.guard';
import { UserServiceService } from './user-service.service';
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
] 
})
export class ServicesModule { }
