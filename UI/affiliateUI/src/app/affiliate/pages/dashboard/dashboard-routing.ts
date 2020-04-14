import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { DashboardComponent } from './dashboard.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { AllProductComponent } from './components/all-product/all-product.component';
import { SharedProductsComponent } from './components/shared-products/shared-products.component';
import { SalesHistoryComponent } from './components/sales-history/sales-history.component';
import { AccDetailsComponent } from './components/acc-details/acc-details.component';
import { EditProfileComponent } from "src/app/affiliate/pages/dashboard/components/edit-profile/edit-profile.component";
import { SettingsComponent } from "src/app/affiliate/pages/dashboard/components/settings/settings.component";

export const DashboardRoutes: Routes = [
  {
    path: "",
    component : DashboardComponent,
    children:[
        {
            path : '',
            redirectTo: "products",
            pathMatch: "full"
        },
        {
            path : 'stats',
            component : StatisticsComponent
        }, 
        {
            path : 'products',
            component : AllProductComponent
        },
        {
            path : 'shared',
            component : SharedProductsComponent
        },
        {
            path : 'history',
            component : SalesHistoryComponent
        },
        {
            path : 'accDetails',
            component : AccDetailsComponent
        },
        {
            path : 'profile',
            component : EditProfileComponent
        },
        {
            path : 'setting',
            component : SettingsComponent
        },
    ]
  }

];
@NgModule({
  imports: [RouterModule.forChild(DashboardRoutes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {}
