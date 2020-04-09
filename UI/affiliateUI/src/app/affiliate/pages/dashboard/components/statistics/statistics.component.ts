import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { UserService } from "src/_services/http_&_login/user-service.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

    public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='orderdate'
  public productList:any;
  constructor(public userService:UserService,public _router : Router,public toastr:ToastrService,) { }

  ngOnInit() {
    this.getTotalComissionGroupByProduct();
  }

  getTotalComissionGroupByProduct()
  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.userService.getTotalComissionGroupByProduct(filter).subscribe(data=>{
      console.log("data us",data);
      this.productList=data.data.productList;
    },error=>{
      console.log("error",error);
    })
  }


}
