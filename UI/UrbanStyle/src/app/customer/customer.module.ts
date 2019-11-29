import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CustomerRoutes } from './customer.routing';
import { HomeComponent } from './home/home.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { CommonModule } from '@angular/common';


@NgModule({
    imports:[
        RouterModule.forChild(CustomerRoutes),
          CommonModule,
    ],
    declarations:[HomeComponent, WishlistComponent],
})

export class CustomerModule{

}