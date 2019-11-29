import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { WishlistComponent } from "src/app/customer/wishlist/wishlist.component";

export const CustomerRoutes: Routes=[
    {
        path:'',
        component : HomeComponent
    },
      {
        path:'wishlist',
        component : WishlistComponent
    }
];