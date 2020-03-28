import { WalletComponent } from './components/wallet/wallet.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserDashboardComponent } from './user-dashboard.component';
import { OrdersComponent } from './components/orders/orders.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { LoginComponent } from '../login/login.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { SettingComponent } from './components/setting/setting.component';
import { UserReviewComponent } from './components/user-review/user-review.component';
import { ReturnsComponent } from './components/returns/returns.component';
import { ManageAddressComponent } from './components/manage-address/manage-address.component';


const routes: Routes = [
    {
        path: '',
        component: UserDashboardComponent,
        children : [
            {
                path : 'profile',
                component : EditProfileComponent
            },
            {
                path : 'address',
                component : ManageAddressComponent
            },
            
            {
                path : 'orders',
                component : OrdersComponent
            },
            {
                path : 'wishlist',
                component : WishlistComponent
            },
            {
                path : 'setting',
                component : SettingComponent
            },
            {
                path : 'review',
                component : UserReviewComponent
            },
            
            {
                path : 'return',
                component : ReturnsComponent
            },

            {
                path : 'wallet',
                component : WalletComponent
            },
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserDashboardRoutingModule { }
