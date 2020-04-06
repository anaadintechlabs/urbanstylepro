import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SingleProductViewComponent } from './pages/single-product-view/single-product-view.component';

const routes : Routes = [
    {
        path: '',
        component : SingleProductViewComponent
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SingleProductViewRoutingModule { }