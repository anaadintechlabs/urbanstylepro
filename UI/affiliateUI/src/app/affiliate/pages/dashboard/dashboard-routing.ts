import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { DashboardComponent } from './dashboard.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { AllProductComponent } from './components/all-product/all-product.component';
import { SharedProductsComponent } from './components/shared-products/shared-products.component';
import { SalesHistoryComponent } from './components/sales-history/sales-history.component';
import { AccDetailsComponent } from './components/acc-details/acc-details.component';

export const DashboardRoutes: Routes = [
  {
    path: "",
    component : DashboardComponent,
    children:[
        {
            path : '',
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
    ]
  }

];
@NgModule({
  imports: [RouterModule.forChild(DashboardRoutes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {}
