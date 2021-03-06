import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { Router, ActivatedRoute, NavigationEnd } from "@angular/router";
import { DataService } from "src/_services/data/data.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Country, State, City } from "src/_modals/country";
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
  @Input() fromVendorSignUp: boolean;
  @Input() addressForm : FormGroup;
  @Output() addressDetails: EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
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
        if (urlData.indexOf("addresses") !== -1) {
          var params = this.route.snapshot.params;
          this.action = params.action;

          if (params.action) {
            if (this.action == "view") {
              this.addressForm.disable({});
            } else {
              this.addressForm.enable({});
            }
            if (this.action == "add") {
              this.address = undefined;
            } else if (params.id) {
              this.addressId = params.id;
              this.getAddressInformation(this.addressId);
            }
          }
        }
      }
    });
  }

  get f() {
    return this.addressForm.controls;
  }

  getAllCountry() {
    this.dataService.getAllCountries("api/getAllCountries").subscribe(data => {
      this.countryList = data;
    });
  }

  countryOnChange(ev: any) {
    let url = "api/getAllStatesOfCountry?countryId=" + ev.target.value;
    this.dataService.getAllStatesOfCountry(url).subscribe(data => {
      this.stateList = data;
    });
  }

  stateOnChange(event) {
    let url = "api/getAllCityOfState?stateId=" + event.target.value;
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
          id: this.userId
        });
      });
  }

  ngOnInit() {
    console.log("inside oninit", this.fromVendorSignUp);
    this.getAllCountry();
  }

  onSubmit() {
    this.addressDetails.emit(this.addressForm);
    if (this.addressForm.status != 'VALID') {
      // this.toast.warning('Please fill all details in mandatory fields');
      return;
    } else {
      
      this.addressForm.controls.user.patchValue({
        id: this.userId
      });
      if (this.fromVendorSignUp) {
        console.log("from vendor sign up");
        this.addressDetails.emit(this.addressForm);
      } else {
        console.log("from add addres");
        this.dataService
          .saveAddressDetails("api/saveAddressDetails", this.addressForm.value)
          .subscribe(
            data => {
              this.router.navigateByUrl("/vendor/account/addresses");

              //this.router.navigateByUrl("secure/" + this.customerId + "/admin/role/edit/" + data.role.id);
            },
            error => {
              // this.toast.error('Something went wrong');
            }
          );
      }
    }
  }

  backButton() {
    this.router.navigateByUrl("vendor/account/addresses");
  }
}
