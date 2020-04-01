import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { EditProductComponent } from './edit-product.component';
import { VitalInfoComponent } from './components/vital-info/vital-info.component';
import { VariantsComponent } from './components/variants/variants.component';
import { ImagesComponent } from './components/images/images.component';
import { ExtraDetailsComponent } from './components/extra-details/extra-details.component';
import { MetaInfoComponent } from './components/meta-info/meta-info.component';


export const AddProduct: Routes=[
    {
        path : '',
        component : EditProductComponent,
        children : [
            {
                path : '',
                redirectTo : 'vitalInfo',
                pathMatch : "full"
            },
            {
                path : 'vitalInfo',
                component : VitalInfoComponent
            },
            {
                path : 'prodDesc',
                component : VariantsComponent,
            },
            {
                path : 'imageUpload',
                component : ImagesComponent,
            },
            {
                path : 'extraDetails',
                component : ExtraDetailsComponent,
            },
            {
                path : 'metaInfo',
                component : MetaInfoComponent,
            }
        ]
    }, 
];

@NgModule({
  imports: [RouterModule.forChild(AddProduct)],
  exports: [RouterModule]
})

export class EditProductRoutes { }