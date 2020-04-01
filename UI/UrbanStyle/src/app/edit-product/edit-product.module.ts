import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditProductComponent } from './edit-product.component';
import { VitalInfoComponent } from './components/vital-info/vital-info.component';
import { VariantsComponent } from './components/variants/variants.component';
import { ExtraDetailsComponent } from './components/extra-details/extra-details.component';
import { MetaInfoComponent } from './components/meta-info/meta-info.component';
import { ImagesComponent } from './components/images/images.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { EditProductRoutes } from './edit-product.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    EditProductComponent,
    VitalInfoComponent,
    VariantsComponent,
    ExtraDetailsComponent,
    MetaInfoComponent,
    ImagesComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    EditProductRoutes
  ]
})
export class EditProductModule { }
