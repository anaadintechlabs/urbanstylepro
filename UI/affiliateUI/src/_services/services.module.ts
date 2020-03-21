import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './http_&_login/api.service';
import { HttpTokenInterceptorService } from './http_&_login/http-token-interceptor.service';
import { JwtServiceService } from './http_&_login/jwt-service.service';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuardService } from './http_&_login/authGuard.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
  ],
  providers:[
    ApiService,
    HttpTokenInterceptorService,
    JwtServiceService,
    AuthGuardService,
  ] 
})
export class ServicesModule { }
