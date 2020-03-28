import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { catchError } from 'rxjs/internal/operators/catchError';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  menu : any[] = [];
  showSearch : boolean = false;

  constructor(
    private _apiService : ApiService
  ) { }

  ngOnInit() {
    this.getAllCategory();
  }


  public getAllCategory() {
    let body = {
      limit : 50,
      offset : 0,
      sortingField : 'createdDate',
      sortingDirection : 'asc'
    }
    return this._apiService.post(urls.menu,body).subscribe(data=>{
      if(data.isSuccess){
        this.menu = data;
        console.log("menu==========>",this.menu)
      } else {

      }
    },err=>{
      catchError(err);
    })
  }
  
  changeSearchStatus(value:boolean) {
    console.log("search captured", value);
    this.showSearch = value;
    console.log("status change", this.showSearch);
  }
}
