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
import { OrderDetailsComponent } from './order-details/order-details.component';

import { NgxPaginationModule } from '../../../node_modules/ngx-pagination';
import { WalletComponent } from './wallet/wallet.component';
import { SettingsComponent } from './settings/settings.component';
import { ReactiveFormsModule } from "@angular/forms";
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ReturnDetailsComponent } from './return-details/return-details.component';

@NgModule({
  imports: [
    VendorRoutingModule,
    CommonModule,
    FormModule,
    ReactiveFormsModule,
    ServicesModule,
    NgbModule,
    ServicesModule,
    SharedModule,
    ModalModule ,
    NgxPaginationModule,
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
    SalesReturnListingComponent,
    OrderDetailsComponent,
    WalletComponent,
    SettingsComponent,
    EditProfileComponent,
    ReturnDetailsComponent
  ],
  bootstrap: [VendorComponent]
})
export class VendorModule {}
