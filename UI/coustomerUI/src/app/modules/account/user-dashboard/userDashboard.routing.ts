import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserDashboardComponent } from './user-dashboard.component';
import { OrdersComponent } from './components/orders/orders.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { LoginComponent } from '../login/login.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { SettingComponent } from './components/setting/setting.component';


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
            }
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserDashboardRoutingModule { }
