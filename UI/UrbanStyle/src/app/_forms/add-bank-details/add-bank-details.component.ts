import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Router, ActivatedRoute, NavigationEnd } from "@angular/router";
import { DataService } from "src/_services/data/data.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { BankDetails } from "src/_modals/bankdetails";

@Component({
  selector: "app-add-bank-details",
  templateUrl: "./add-bank-details.component.html",
  styleUrls: ["./add-bank-details.component.scss"]
})
export class AddBankDetailsComponent implements OnInit {
  bankId: any;
  public action: any = "add";
  public userId: any;

  public bankDetail: BankDetails;
  @Input() fromVendorSignUp: boolean;
  @Output()
  bankDetailsVendor: EventEmitter<FormGroup> = new EventEmitter<FormGroup>();

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
        if (urlData.indexOf("bank_details") !== -1) {
          var params = this.route.snapshot.params;
          this.action = params.action;

          if (params.action) {
            if (this.action == "view") {
              this.bankDetailForm.disable({});
            } else {
              this.bankDetailForm.enable({});
            }
            if (this.action == "add") {
              this.bankDetail = undefined;
            } else if (params.id) {
              this.bankId = params.id;
              this.getBankInformation(this.bankId);
            }
          }
        }
      }
    });
  }

  bankDetailForm = new FormGroup({
    id: new FormControl(""),
    bankName: new FormControl("", [
      Validators.required,
      Validators.minLength(3)
    ]),
    accountNumber: new FormControl("", [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(255)
    ]),
    status: new FormControl(1),
    accType: new FormControl("", [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(10)
    ]),
    ifscCode: new FormControl("", [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(15)
    ]),

    user: new FormGroup({
      id: new FormControl("")
    })
  });

  get f() {
    return this.bankDetailForm.controls;
  }

  getBankInformation(bankId) {
    console.log("bank is ", bankId);
    this.dataService
      .getBankInformation("api/getBankDetailsById", bankId)
      .subscribe(data => {
        this.bankDetail = data;
        console.log("bank is", this.bankDetail);
        this.bankDetailForm.patchValue(this.bankDetail);
        this.bankDetailForm.controls.user.patchValue({
          id: this.userId
        });
      });
  }

  ngOnInit() {}

  onSubmit() {
    if (this.bankDetailForm.invalid) {
      // this.toast.warning('Please fill all details in mandatory fields');
      return;
    } else {
      if (this.fromVendorSignUp) {
        console.log("from vendor sign up");
        this.bankDetailsVendor.emit(this.bankDetailForm);
      } else {
        console.log("from add address");
        this.bankDetailForm.controls.user.patchValue({
          id: this.userId
        });

        this.dataService
          .saveBankDetails("api/saveBankDetails", this.bankDetailForm.value)
          .subscribe(
            data => {
              if (data.duplicate) {
                alert("duplicte ifsc code");
              } else {
                // alert("sucess");
                this.router.navigateByUrl('/vendor/account/bank_details');

              }
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
    this.router.navigateByUrl("vendor/account/bank_details");
  }
}
