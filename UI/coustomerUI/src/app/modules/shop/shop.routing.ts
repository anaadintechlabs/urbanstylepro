import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SingleProductViewComponent } from './pages/single-product-view/single-product-view.component';
import { ShopComponent } from './pages/shop/shop.component';

const routes: Routes = [
    {
        path : '',
        component : ShopComponent
    },
    {
        path : 'shop',
        component : ShopComponent
    },
    {
        path: 'product/:id',
        component: SingleProductViewComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ShopRoutingModule { }