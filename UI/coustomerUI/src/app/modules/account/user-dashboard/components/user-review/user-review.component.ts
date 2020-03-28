import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../../../_service/http_&_login/user-service.service';
import { ToastrService } from '../../../../../../../node_modules/ngx-toastr';

@Component({
  selector: 'app-user-review',
  templateUrl: './user-review.component.html',
  styleUrls: ['./user-review.component.scss']
})
export class UserReviewComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'
  public reviewList:any;

  constructor(public userService:UserService,public toastr:ToastrService) { }

  ngOnInit(): void {
    this.getAllReviewsOfUser();
  }

  getAllReviewsOfUser()
  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.userService.getAllReviewsOfUser(filter).subscribe(data=>{
      console.log("data us",data);
      this.reviewList=data.data.reviewList;
      console.log("data us",this.reviewList);
    },error=>{
      console.log("error",error);
    })
  }

  softDeleteProductReview(id)
  {
    this.userService.softDeleteProductReview(id).subscribe(data=>{
      console.log("data deleted successsfully");
      this.toastr.success("Review deleted successfully");
      this.getAllReviewsOfUser();
    },error=>{
      this.toastr.error("Something went wrong");
      console.log("error",error);
    })
  }
}

