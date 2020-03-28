import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { User } from 'src/_modals/user';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { HttpParams } from '@angular/common/http';
import { urls } from 'src/constants/urlLists';
import { WishlistService } from 'src/_service/product/wishlist.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss']
})
export class UserDashboardComponent implements OnInit {

  public user : User;
  constructor(
    private _userService : UserService,
    private _apiService : ApiService,
    private _wishList : WishlistService
  ) { 
    this.user = JSON.parse(this._userService.getUser());
    this._userService.isAuthenticated.subscribe(data=>{
      this.initializeWishList();
    })
  }

  ngOnInit() {
    if(this.user){
      if(Object.keys(this.user).length){
        this.getWishListOfUser(this.user.id);
      }
    }
  }

  getWishListOfUser(id:number) {
    console.log(id);
    let param : HttpParams = new HttpParams();
    param.set('userId',id.toString());
    let body = {
      "limit":15,
      "offset":0,
      "sortingDirection":"asc",
      "sortingField":"createdDate"
    }
    this._apiService.post(`${urls.wishList}?userId=${id}`,body).subscribe( data=> {
      console.log(data);
      if(data.data.wishList.length){
        localStorage.removeItem('wishlistItems');
        localStorage.setItem('wishlistItems', data.data.wishList);
      }
    })
  }

  initializeWishList() {
    this.getWishListOfUser(this.user.id);
  }

}
