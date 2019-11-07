import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CustomerRoutes } from './customer.routing';
import { HomeComponent } from './home/home.component';


@NgModule({
    imports:[
        RouterModule.forChild(CustomerRoutes),
    ],
    declarations:[HomeComponent],
})

export class CustomerModule{

}