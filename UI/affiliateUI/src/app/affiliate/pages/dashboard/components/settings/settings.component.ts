import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FormControl } from "@angular/forms";
import { Validators } from "@angular/forms";
import { UserService } from "src/_services/http_&_login/user-service.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

 constructor(public toastr:ToastrService,public userService: UserService) { }

  passwordFormGroup= new FormGroup({
    oldPassword:new FormControl('',[Validators.required]),
    newPassword: new FormControl('',[Validators.required]),
    confirmPassword:new FormControl('',[Validators.required])
  })

  ngOnInit() {
  }

  onSubmit()
  {
   if(this.passwordFormGroup.invalid)
   {
    this.toastr.warning("Please fill all the details");
   }
   else if(this.passwordFormGroup.controls.newPassword.value !=   this.passwordFormGroup.controls.confirmPassword.value)
   {
    this.toastr.warning("Password does not match");
   }
   else
   {
      this.userService.changePassword(this.passwordFormGroup.value).subscribe(data=>{
        this.toastr.success("password changes success");
      },error=>{

        this.toastr.warning(error.message);
      })
   }
  }

}
