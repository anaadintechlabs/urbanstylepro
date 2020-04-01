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
import { AddProductHeaderComponent } from './add-product-header/add-product-header.component';
import { ModalModule } from 'ngx-modal'
import { ToastrModule } from "ngx-toastr";
import { OrderListingComponent } from './order-listing/order-listing.component';
import { SalesListingComponent } from './sales-listing/sales-listing.component';
import { SalesReturnListingComponent } from './sales-return-listing/sales-return-listing.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    VendorRoutingModule,
    CommonModule,
    FormModule,
    ServicesModule,
    NgbModule,
    ServicesModule,
    SharedModule,
    ModalModule ,
    ToastrModule.forRoot() // ToastrModule added
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
    AddProductHeaderComponent,
    OrderListingComponent,
    SalesListingComponent,
    SalesReturnListingComponent
  ],
  bootstrap: [VendorComponent]
})
export class VendorModule {}
