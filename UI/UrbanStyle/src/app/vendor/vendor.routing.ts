import { Routes, RouterModule } from "@angular/router";
import { VendorComponent } from './vendor.component';
import { AddProductComponent } from './add-product/add-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgModule } from "@angular/core";
import { InventoryComponent } from "src/app/vendor/inventory/inventory.component";

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
                children : [
                    {
                        path : 'addProduct',
                        component : AddProductComponent
                    },
                ]
            }
        ]
    }
];
@NgModule({
  imports: [RouterModule.forRoot(VendorRoutes)],
  exports: [RouterModule]
})
export class VendorRoutingModule { }