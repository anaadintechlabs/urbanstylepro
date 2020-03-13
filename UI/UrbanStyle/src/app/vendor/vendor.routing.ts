import { Routes, RouterModule } from "@angular/router";
import { VendorComponent } from './vendor.component';
import { AddProductComponent } from './add-product/add-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgModule } from "@angular/core";
import { InventoryComponent } from "src/app/vendor/inventory/inventory.component";
import { OrderListingComponent } from "src/app/vendor/order-listing/order-listing.component";
import { SalesListingComponent } from 'src/app/vendor/sales-listing/sales-listing.component';
import { SalesReturnListingComponent } from './sales-return-listing/sales-return-listing.component';

export const VendorRoutes: Routes=[
    {
        path:'',
        component : VendorComponent,
        children : [
            {
                path : '',
                component : DashboardComponent
            },
            {
                path : 'inventory',
                component : InventoryComponent
            },
            {
                path : 'dashboard',
                component : DashboardComponent,
            },
             {
                path : 'order',
                component : OrderListingComponent,
            },
            {
                path : 'sales',
                component : SalesListingComponent,
            },
            {
                path : 'returns',
                component : SalesReturnListingComponent,
            },
            
            {
                path : 'addProduct',
                loadChildren : ()=> import('./add-product/addProduct.module').then(m => m.AddProductModule)

            }
        ]
    }
];
@NgModule({
  imports: [RouterModule.forChild(VendorRoutes)],
  exports: [RouterModule]
})
export class VendorRoutingModule { }