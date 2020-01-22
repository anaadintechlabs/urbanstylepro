import { NgModule } from "@angular/core";
// import { RouterModule } from "@angular/router";
import { VendorRoutingModule } from "./vendor.routing";

import { CommonModule } from "@angular/common";
import { VendorComponent } from "./vendor.component";
import { HeaderComponent } from "./header/header.component";
import { SidebarComponent } from "./sidebar/sidebar.component";

import { FormModule } from "../_forms/form.module";
import { ServicesModule } from "src/_services/services.module";
// import { AddProductComponent } from "./add-product/add-product.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { InventoryComponent } from "./inventory/inventory.component";
import { FooterComponent } from './footer/footer.component';


@NgModule({
  imports: [
    VendorRoutingModule,
    CommonModule,
    FormModule,
    ServicesModule,
    NgbModule,
    ServicesModule,
    // RouterModule
  ],

  declarations: [
    VendorComponent,
    HeaderComponent,
    SidebarComponent,
    // AddProductComponent,
    DashboardComponent,
    InventoryComponent,
    FooterComponent,
  ],
  bootstrap: [VendorComponent]
})
export class VendorModule {}
