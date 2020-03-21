import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing';
import { DashboardComponent } from './dashboard.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { AllProductComponent } from './components/all-product/all-product.component';
import { SharedProductsComponent } from './components/shared-products/shared-products.component';
import { SalesHistoryComponent } from './components/sales-history/sales-history.component';
import { AccDetailsComponent } from './components/acc-details/acc-details.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [
    DashboardComponent,
    SideMenuComponent,
    AllProductComponent,
    SharedProductsComponent,
    SalesHistoryComponent,
    AccDetailsComponent,
    StatisticsComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule
  ]
})
export class DashboardModule { }
