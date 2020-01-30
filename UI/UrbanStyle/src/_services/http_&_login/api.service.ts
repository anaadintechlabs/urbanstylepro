import { environment } from "./../../environments/environment";
import { JwtServiceService } from "./jwt-service.service";
import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpParams,
  HttpHeaders,
  HttpBackend
} from "@angular/common/http";
import { catchError } from "rxjs/internal/operators/catchError";
import { Observable, throwError } from "rxjs";

@Injectable()
export class ApiService {
//  userUrl='http://localhost:8081/urban/';
userUrl='https://user2.cfapps.io/urban/';
  constructor(
    private http: HttpClient,
    private jwtService: JwtServiceService,
    private httpBackend: HttpBackend
  ) {}

  private formatErrors(error: any) {
    console.log("errir ",error)
    return throwError(error.error);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http
      .get(`${environment.api_url}${path}`, { params })
      .pipe(catchError(this.formatErrors));
  }

   getUser(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http
      .get(this.userUrl + path, { params })
      .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http
      .put(`${environment.api_url}${path}`, JSON.stringify(body))
      .pipe(catchError(this.formatErrors));
  }

  post(path: string, body: Object = {}): Observable<any> {
    console.log("path..." + environment.api_url + path);
    console.log("body..." , body);
    return this.http
      .post(`${environment.api_url}${path}`, body)
      .pipe(catchError(this.formatErrors));
  }

    postUser(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(this.userUrl + path, body)
      .pipe(catchError(this.formatErrors));
  }

  postWithMedia(path: string, body): Observable<any> {
    const HttpUploadOptions = {
      headers: new HttpHeaders({
        "Content-Type": "multipart/form-data"
        // Authorization: "Bearer " + this.jwtTokenService.getToken()
      })
    };
    this.http = new HttpClient(this.httpBackend);

    console.log("path..." + environment.api_url + path);
    console.log("body..."+ body);
    return this.http
      .post(`${environment.api_url}${path}`, body)
      .pipe(catchError(this.formatErrors));
  }

  delete(path): Observable<any> {
    return this.http
      .delete(`${environment.api_url}${path}`)
      .pipe(catchError(this.formatErrors));
  }

   deleteUser(path): Observable<any> {
    return this.http
      .delete(this.userUrl + path)
      .pipe(catchError(this.formatErrors));
  }

}
