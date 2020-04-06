import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopRoutingModule } from './shop.routing';
import { SharedModule } from 'src/app/shared/shared.module';
import { ShopComponent } from './pages/shop/shop.component';
import { FiltersComponent } from './components/filters/filters.component';
import { ShopHeaderComponent } from './components/shop-header/shop-header.component';
import { AllProductsComponent } from './components/all-products/all-products.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BlockModule } from '../blocks/block.module';



@NgModule({
  declarations: [
    ShopComponent,
    FiltersComponent,
    ShopHeaderComponent,
    AllProductsComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ShopRoutingModule,
    SharedModule,
    BlockModule
  ]
})
export class ShopModule { }
