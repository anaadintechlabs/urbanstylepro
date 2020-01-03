import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { Router, ActivatedRoute, NavigationEnd } from "@angular/router";
import { DataService } from "src/_services/data/data.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-add-category",
  templateUrl: "./add-category.component.html",
  styleUrls: ["./add-category.component.scss"]
})
export class AddCategoryComponent implements OnInit {
  shoParent: boolean;
  category: any;
  categoryId: any;
  public action: any = "add";
  public userId: any;

  constructor(
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    //get dynamic logged in user
    this.userId = 1;

    this.router.events.forEach(event => {
      if (event instanceof NavigationEnd) {
        var urlData = event.url.split("/");
        //Means URL has groupdefinition
        if (urlData.indexOf("category") !== -1) {
          var params = this.route.snapshot.params;
          this.action = params.action;

          if (params.action) {
            if (this.action == "view") {
              this.categoryForm.disable({});
            } else {
              this.categoryForm.enable({});
            }
            if (this.action == "add") {
              this.category = undefined;
            } else if (params.id) {
              this.categoryId = params.id;
              this.getAddressInformation(this.categoryId);
            }
          }
        }
      }
    });
  }

  categoryForm = new FormGroup({
    categoryId: new FormControl(""),
    categoryName: new FormControl("", [
      Validators.required,
      Validators.minLength(2)
    ]),
    categoryCode: new FormControl("", [
      Validators.required,
      Validators.minLength(2)
    ]),
    status: new FormControl(1),
    isParent: new FormControl(true),
    parentCategory: new FormGroup({
      categoryId: new FormControl("")
    }),
    state: new FormGroup({
      id: new FormControl("")
    })
  });

  get f() {
    return this.categoryForm.controls;
  }

  isParentCheck(data) {
    this.shoParent = data;
  }
  getAddressInformation(categoryId) {
    this.dataService
      .getAddressInformation("api/getAddressById", categoryId)
      .subscribe(data => {
        this.category = data;

        this.categoryForm.patchValue(this.category);
      });
  }

  ngOnInit() {}

  onSubmit() {
    if (this.categoryForm.invalid) {
      // this.toast.warning('Please fill all details in mandatory fields');
      return;
    } else {
      this.dataService
        .saveAddressDetails("api/saveAddressDetails", this.categoryForm.value)
        .subscribe(
          data => {
            this.router.navigateByUrl("/admin/category/");
            //this.router.navigateByUrl("secure/" + this.customerId + "/admin/role/edit/" + data.role.id);
          },
          error => {
            // this.toast.error('Something went wrong');
          }
        );
    }
  }

  backButton() {
    this.router.navigateByUrl("/admin/category/");
  }
}
