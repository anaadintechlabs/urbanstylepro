import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AffiliateRoutes } from './affiliate.routing';

@NgModule({
    imports:[
        RouterModule.forChild(AffiliateRoutes),
    ],
    declarations:[
    ],
})

export class AffiliateModule{

}