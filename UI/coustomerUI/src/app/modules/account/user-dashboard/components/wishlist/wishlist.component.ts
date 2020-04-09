import { Component, OnInit } from '@angular/core';
import { WishlistService } from 'src/_service/product/wishlist.service';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'

  public wishlist:any;

  public 
  constructor(
    public _wishList : WishlistService,
    public toast:ToastrService
  ) { }


  ngOnInit() {
      this.getWishlist();
  }


  getWishlist()
  {  
    let filter={
    'offset':this.offset,
    'limit':this.limit,
    'sortingDirection':this.sortingDirection,
    'sortingField':this.sortingField

  }
  this._wishList.getAllWishListOfUser(filter).subscribe(data=>{
    console.log("data us",data);
    this.wishlist=data.wishList;
    console.log(this.wishlist)
  },error=>{
    console.log("error",error);
  })
  }

  softDeleteWishlist(id)
  {
    this._wishList.softDeleteWishlist(id).subscribe(data=>{
      console.log("data deleted successsfully");
      this.wishlist=data.data.wishList;
      this.toast.success("Product removed from wishlist","Success");
      console.log("data us",data);
    },error=>{
      console.log("error",error);
    })
  }
}
