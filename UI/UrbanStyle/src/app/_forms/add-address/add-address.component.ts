import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute, NavigationEnd } from "@angular/router";
import { DataService } from "src/_services/data/data.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Country, State,City } from "src/_modals/country";
import { Address } from "src/_modals/address";

@Component({
  selector: "app-add-address",
  templateUrl: "./add-address.component.html",
  styleUrls: ["./add-address.component.scss"]
})
export class AddAddressComponent implements OnInit {
  addressId: any;
  public action: any = "add";
  public userId: any;
  public countryList: Country[];
  public stateList: State[];
  public cityList: City[];
  public address: Address;

  constructor(
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    console.log("construc add addreess")
      //get dynamic logged in user
    this.userId = 1;

    this.router.events.forEach(event => {
      if (event instanceof NavigationEnd) {
        var urlData = event.url.split("/");
        //Means URL has groupdefinition
        if (urlData.indexOf("addresses") !== -1) {
          var params = this.route.snapshot.params;
          this.action = params.action;

          this.getAllCountry();
          if (params.action) {
            if (this.action == "view") {
              this.addressForm.disable({});
            } else {
              this.addressForm.enable({});
            }
            if (this.action == "add") {
              this.address=undefined;
            } else if (params.id) {
              this.addressId = params.id;
              this.getAddressInformation(this.addressId);
            }
          }
        }
      }
    });
  }

  addressForm = new FormGroup({
    id: new FormControl(""),
    addressOne: new FormControl("", [
      Validators.required,
      Validators.minLength(4)
    ]),
    addressTwo: new FormControl("", [
      Validators.required,
      Validators.minLength(10),
      Validators.maxLength(255)
    ]),
     status: new FormControl(1),
    zip: new FormControl("", [
      Validators.required,
      Validators.minLength(6),
      Validators.maxLength(6)
    ]),
    country: new FormGroup({
      id: new FormControl("")
    }),
    state: new FormGroup({
      id: new FormControl("")
    }),
    cite: new FormGroup({
      id: new FormControl("")
    }),
     user: new FormGroup({
      id: new FormControl("")
    })
  });

  get f() {
    return this.addressForm.controls;
  }

  getAllCountry() {
    this.dataService.getAllCountries("api/getAllCountries").subscribe(data => {
      this.countryList = data;
    });
  }

  countryOnChange(event) {
    let url = "api/getAllStatesOfCountry?countryId=" + event;
    this.dataService.getAllStatesOfCountry(url).subscribe(data => {
      this.stateList = data;
    });
  }

  stateOnChange(event) {
    let url = "api/getAllCityOfState?stateId=" + event;
    this.dataService.getAllCityOfState(url).subscribe(data => {
      this.cityList = data;
    });
  }

  getAddressInformation(addressId) {
    this.dataService
      .getAddressInformation("api/getAddressById", addressId)
      .subscribe(data => {
        this.address = data;
        this.countryOnChange(data.country.id);
        this.stateOnChange(data.state.id);
        this.addressForm.patchValue(this.address);
         this.addressForm.controls.user.patchValue({
          id: this.userId,
        });
      });
  }

  ngOnInit() {
   
  
  }


    onSubmit() {
    if (this.addressForm.invalid) {
     // this.toast.warning('Please fill all details in mandatory fields');
      return;
    }
    else {


        //    console.log('form value is', this.createRoleForm.value);
        this.addressForm.controls.user.patchValue({
          id: this.userId,
        });
        this.addressForm.controls.st
        // console.log("this.createRoleForm.value"+this.createRoleForm.value)
        this.dataService.saveAddressDetails("api/saveAddressDetails",this.addressForm.value)
          .subscribe(data => {
            alert("sucess");
              //this.router.navigateByUrl("secure/" + this.customerId + "/admin/role/edit/" + data.role.id);
          }, error => {
           // this.toast.error('Something went wrong');
          });
    }
  }


    backButton() {

    this.router.navigateByUrl("vendor/account/addresses");


  }

}
