import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { Router, ActivatedRoute, NavigationEnd } from "@angular/router";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Address,Country, State, City } from "../../../../../../_modals/address";
import { UserService } from "../../../../../../_service/http_&_login/user-service.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.scss']
})
export class AddAddressComponent implements OnInit {



      addressForm = new FormGroup({
      id: new FormControl(""),
      addressOne: new FormControl("", [
        Validators.required,
        Validators.minLength(2)
      ]),
      addressTwo: new FormControl("", [
        Validators.required,
        Validators.minLength(2),
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

 addressId: any;
  public action: any = "add";
  public userId: any;
  public countryList: Country[];
  public stateList: State[];
  public cityList: City[];
  public address: Address;
  
  


  constructor(
    private dataService: UserService,
    private route: ActivatedRoute,
    private router: Router ,
  private toast:ToastrService ) {
    //get dynamic logged in user
     let currunt_user = JSON.parse(this.dataService.getUser());
      if (currunt_user && currunt_user.token) {
       this.userId = currunt_user.id;
    }
   

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
    this.getAllCountry();
  }

  onSubmit() {
    if (this.addressForm.status != 'VALID') {
       this.toast.warning('Please fill all details in mandatory fields');
      return;
    } else {
      
      this.addressForm.controls.user.patchValue({
        id: this.userId
      });

        console.log("from add addres");
        this.dataService
          .saveAddressDetails("api/saveAddressDetails", this.addressForm.value)
          .subscribe(
            data => {
              this.router.navigateByUrl("/account/dashboard/address");
               this.toast.success('Address added successfully',"Success");
              //this.router.navigateByUrl("secure/" + this.customerId + "/admin/role/edit/" + data.role.id);
            },
            error => {
              // this.toast.error('Something went wrong');
            }
          );
      
    }
  }

  backButton() {
    this.router.navigateByUrl("/account/dashboard/address");
  }
}
