import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AffiliateRoutes } from './affiliate.routing';
import { LoginComponent } from './login/login.component';

@NgModule({
    imports:[
        RouterModule.forChild(AffiliateRoutes),
    ],
    declarations:[LoginComponent],
})

export class AffiliateModule{

}