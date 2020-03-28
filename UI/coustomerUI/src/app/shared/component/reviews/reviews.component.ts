import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/_service/http_&_login/user-service.service';
import { ApiService } from 'src/_service/http_&_login/api.service';
import { urls } from 'src/constants/urlLists';
import { CartService } from 'src/_service/product/cart.service';
import { WishlistService } from 'src/_service/product/wishlist.service';

@Component({
  selector: 'reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})

export class ReviewsComponent implements OnInit {

  @Input() reviews : any[] = [];

  public userRating : string = "";


  productID : string = '';
  constructor(
    private _fb : FormBuilder,
    private _route : ActivatedRoute,
    public _userService : UserService,
    private _apiService : ApiService,
  ) { 
    this._route.paramMap.subscribe(param=>{
      this.productID = param.get('id');
    })
  }

  ngOnInit() {
  }

  submit() {
    let user = this.reviewForm.get('user') as FormGroup;
    user.get('id').setValue(this.getCurruntUserId());
    let product = this.reviewForm.get('product') as FormGroup;
    product.get('productVariantId').setValue(this.productID);
    this.reviewForm.get('rating').patchValue(this.userRating);
    if(this.reviewForm.status == 'VALID') {
      console.log(this.reviewForm.value);
      this._apiService.post(urls.submitReview,this.reviewForm.value).subscribe(res=>{
        console.log(res);
        this.reviews.push(res.data.productReview);
        console.log(this.reviews);
        this.reviewForm.reset();
        this.userRating = '';
      })
    }
  }

  selectRating(rating : number) {
    this.userRating = rating.toString();
  }

  getCurruntUserId() {
    let user = this._userService.getUser();
    return JSON.parse(user).id;
  }

  public reviewForm : FormGroup = this._fb.group({
    user : this._fb.group({
      id : new FormControl('', Validators.required)
    }),
    product : this._fb.group({
      productVariantId : new FormControl('' , Validators.required)
    }),
    review : new FormControl('', Validators.required),
    rating: new FormControl(''),
    title: new FormControl('', Validators.required)
  });

}
