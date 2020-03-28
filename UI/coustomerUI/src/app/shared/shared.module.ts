import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OwlModule } from 'ngx-owl-carousel';
import { ProductCardComponent } from './component/product-card/product-card.component';
import { SideMenuDirective } from './directives/sideMenu';
import { ImageSliderComponent } from './component/image-slider/image-slider.component';
import { ProductDescriptionComponent } from './component/product-description/product-description.component';
import { ReviewsComponent } from './component/reviews/reviews.component';
import { ProductCarouselComponent } from './component/product-carousel/product-carousel.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductCard2Component } from './component/product-card2/product-card2.component';
import { CurrencyFormatPipe } from './pipes/curruncy-formator';
import { BredcrumbComponent } from './component/bredcrumb/bredcrumb.component';
import { RouterModule } from '@angular/router';
import { Carousal2Component } from './component/carousal2/carousal2.component';

@NgModule({
    declarations: [
        ProductCardComponent,
        SideMenuDirective,
        ImageSliderComponent,
        ProductDescriptionComponent,
        ReviewsComponent,
        ProductCarouselComponent,
        ProductCard2Component,
        CurrencyFormatPipe,
        BredcrumbComponent,
        Carousal2Component
    ],
    imports: [ 
        CommonModule,
        OwlModule,
        FormsModule,
        RouterModule,
        ReactiveFormsModule 
    ],
    exports: [
        ProductCardComponent,
        ImageSliderComponent,
        ProductDescriptionComponent,
        ReviewsComponent,
        ProductCarouselComponent,
        SideMenuDirective,
        ProductCard2Component,
        CurrencyFormatPipe,
        BredcrumbComponent,
        Carousal2Component
    ],
    providers: [],
})
export class SharedModule {}