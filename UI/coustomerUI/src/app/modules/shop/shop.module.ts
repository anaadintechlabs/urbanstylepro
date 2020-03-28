import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingleProductViewComponent } from './pages/single-product-view/single-product-view.component';
import { ShopRoutingModule } from './shop.routing';
import { SharedModule } from 'src/app/shared/shared.module';
import { ShopComponent } from './pages/shop/shop.component';
import { FiltersComponent } from './components/filters/filters.component';
import { ShopHeaderComponent } from './components/shop-header/shop-header.component';
import { AllProductsComponent } from './components/all-products/all-products.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FeatueDetailsComponent } from './components/featue-details/featue-details.component';
import { ProdDescComponent } from './components/prod-desc/prod-desc.component';
import { ReviewCardComponent } from './components/review-card/review-card.component';
import { BlockModule } from '../blocks/block.module';



@NgModule({
  declarations: [
    SingleProductViewComponent,
    ShopComponent,
    FiltersComponent,
    ShopHeaderComponent,
    AllProductsComponent,
    FeatueDetailsComponent,
    ProdDescComponent,
    ReviewCardComponent
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
