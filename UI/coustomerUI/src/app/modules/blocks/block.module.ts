import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BlockFeatureProductComponent } from './feature-product/feature-product.component';
import { BlockBannerComponent } from './block-banner/block-banner.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { BlockCategoryComponent } from './block-category/block-category.component';
import { BrandSliderComponent } from './brand-slider/brand-slider.component';
import { OwlModule } from 'ngx-owl-carousel';

@NgModule({
    declarations: [
        BlockFeatureProductComponent,
        BlockBannerComponent,
        BlockCategoryComponent,
        BrandSliderComponent
    ],
    imports: [ 
        CommonModule,
        SharedModule,
        OwlModule,
     ],
    exports: [
        BlockFeatureProductComponent,
        BlockBannerComponent,
        BlockCategoryComponent,
        BrandSliderComponent
    ],
    providers: [],
})
export class BlockModule {}