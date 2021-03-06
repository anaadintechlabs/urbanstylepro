import { JwtServiceService } from './jwt-service.service';
import { Injectable } from '@angular/core';
import { HttpInterceptor,HttpRequest, HttpHandler,HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class HttpTokenInterceptorService  implements HttpInterceptor {

  constructor(private jwtService: JwtServiceService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const headersConfig = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    const token = this.jwtService.getToken();

    if (token) {
      headersConfig['Authorization'] = `Bearer ${token}`;
    }

    const request = req.clone({ setHeaders: headersConfig });
    return next.handle(request);
  }

}
