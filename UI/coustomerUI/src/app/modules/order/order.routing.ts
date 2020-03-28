import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OrderComponent } from './order.component';
import { combineAll } from 'rxjs/operators';


const routes: Routes = [
    {
        path : '',
        component : OrderComponent
    },
    {
        path : 'checkout',
        component : OrderComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OrderRoutingModule { }