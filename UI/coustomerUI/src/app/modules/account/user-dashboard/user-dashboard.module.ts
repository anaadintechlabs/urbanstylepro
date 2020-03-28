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

@NgModule({
  declarations: [
    UserDashboardComponent,
    WishlistComponent,
    OrdersComponent,
    NavigationComponent,
    EditProfileComponent,
    SettingComponent,
  ],
  imports: [
    CommonModule,
    UserDashboardRoutingModule,
    SharedModule
  ]
})
export class UserDashboardModule { }
