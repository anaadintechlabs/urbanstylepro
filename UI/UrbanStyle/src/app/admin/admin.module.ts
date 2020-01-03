import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";
import { adminRoutes } from "src/app/admin/admin.routing";
import { AdminComponent } from "src/app/admin/admin.component";
import { FormModule } from "src/app/_forms/form.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { ServicesModule } from "src/_services/services.module";
import { CategroryComponent } from './categrory/categrory.component';
import { AttributeComponent } from './attribute/attribute.component';



@NgModule({
  declarations: [AdminComponent, CategroryComponent, AttributeComponent],
  imports: [
    CommonModule,
        RouterModule.forChild(adminRoutes),
         CommonModule,
        FormModule,
        NgbModule,
        ServicesModule
  ]
})
export class AdminModule { }
