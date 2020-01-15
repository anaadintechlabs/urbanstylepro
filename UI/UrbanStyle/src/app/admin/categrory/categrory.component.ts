import { Component, OnInit, ViewChild } from "@angular/core";

import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatPaginator,
  MatSort,
  MatTableDataSource
} from "@angular/material";
import { DataService } from "src/_services/data/data.service";
import { Router, ActivatedRoute } from "@angular/router";
import { User } from 'src/_modals/user.modal';
@Component({
  selector: 'app-categrory',
  templateUrl: './categrory.component.html',
  styleUrls: ['./categrory.component.scss']
})
export class CategroryComponent implements OnInit {

 @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  categoryList: any = [];
  bankDetailCount: number;
  public userId: any = 1;
  public ELEMENT_DATA: any;
  public dataSource: any;

  public limit: any = 5;
  public offset: any = 0;
  public pageSize: any;
  public listLength: any;
  pageNumber: any;
  user : User;

  constructor(
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    //getUserId Dynamic
    // this.user =  JSON.parse(window.localStorage.getItem('user'));
    // if(this.user.token){
    //   this.userId = this.user.id;

      this.getAllCategories();
  //  } 
  }

  ngOnInit() {}

  getAllCategories() {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    this.dataService
      .getAllCategories("category/getAllCategories", body)
      .subscribe(
        data => {
          this.categoryList = data;
          this.ELEMENT_DATA = this.categoryList;
          this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        },
        error => {
          console.log("error======", error);
        }
      );
  }

navigateToAddCategory(){
      this.router.navigateByUrl(
        "admin/category/add"
        
      ); 
}

  changeStatusOfCategory(categoryId, status) {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    let url =
      "category/changeStatusOfCategory?categoryId=" +
      categoryId +
      "&status=" +
      status;
    this.dataService.changeStatusOfCategory(url, body).subscribe(
      data => {
        this.categoryList = data;
        this.ELEMENT_DATA = this.categoryList;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
      },
      error => {
        console.log("error======", error);
      }
    );
  }

  pageEvent(event) {}
}