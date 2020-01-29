import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { AddProductComponent } from './add-product.component';
import { CategotySelectionComponent } from '../categoty-selection/categoty-selection.component';
import { VitalInformationComponent } from '../vital-information/vital-information.component';
import { AddProductDesciprionComponent } from '../variation/add-product-desciprion.component';
import { ExtraDetailsComponent } from '../extra-details/extra-details.component';
import { MetaInfoComponent } from '../meta-info/meta-info.component';
import { AddProductMediaComponent } from '../add-product-media/add-product-media.component';
import { AddProductGuard } from 'src/_gaurds/addProduct/addProduct.gaurd';


export const AddProduct: Routes=[
    {
        path : '',
        component : AddProductComponent,
        children : [
            {
                path : '',
                redirectTo : 'categorySelection',
                pathMatch : 'full',        
            },
            {
                path : 'categorySelection',
                component : CategotySelectionComponent
            },
            {
                path : 'vitalInfo',
                component : VitalInformationComponent,
                canActivate : [AddProductGuard]
            },
            {
                path : 'prodDesc',
                component : AddProductDesciprionComponent,
                canActivate : [AddProductGuard]
            },
            {
                path : 'imageUpload',
                component : AddProductMediaComponent,
                canActivate : [AddProductGuard]
            },
            {
                path : 'extraDetails',
                component : ExtraDetailsComponent,
                canActivate : [AddProductGuard]
            },
            {
                path : 'metaInfo',
                component : MetaInfoComponent,
                canActivate : [AddProductGuard]
            }
        ]
    },
  
];

@NgModule({
  imports: [RouterModule.forChild(AddProduct)],
  exports: [RouterModule]
})

export class AddProductRoutes { }