import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingleProductViewComponent } from './pages/single-product-view/single-product-view.component';
import { SingleProductViewRoutingModule } from './single-product-view.routing';
import { SharedModule } from '../shared/shared.module';
import { FeatueDetailsComponent } from './components/featue-details/featue-details.component';
import { ProdDescComponent } from './components/prod-desc/prod-desc.component';



@NgModule({
  declarations: [
    SingleProductViewComponent,
    FeatueDetailsComponent,
    ProdDescComponent
  ],
  imports: [
    CommonModule,
    SingleProductViewRoutingModule,
    SharedModule
  ]
})
export class SingleProductViewModule { }
