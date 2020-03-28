import { UserService } from 'src/_service/http_&_login/user-service.service';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.scss']
})
export class SettingComponent implements OnInit {

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
