import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDashboardRoutingModule } from './userDashboard.routing';
import { UserDashboardComponent } from './user-dashboard.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { OrdersComponent } from './components/orders/orders.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { SettingComponent } from './components/setting/setting.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { UserReviewComponent } from './components/user-review/user-review.component';
import { ReturnsComponent } from './components/returns/returns.component';
import { ManageAddressComponent } from './components/manage-address/manage-address.component';
import { WalletComponent } from './components/wallet/wallet.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { OrderDetailsComponent } from './components/order-details/order-details.component'; // <-- import the module

@NgModule({
  declarations: [
    UserDashboardComponent,
    WishlistComponent,
    OrdersComponent,
    NavigationComponent,
    EditProfileComponent,
    SettingComponent,
    UserReviewComponent,
    ReturnsComponent,
    ManageAddressComponent,
    WalletComponent,
    OrderDetailsComponent,
  ],
  imports: [
    CommonModule,
    UserDashboardRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class UserDashboardModule { }
