import { NgModule } from "@angular/core";
import { AddProductRoutes } from './addProduct.routing';
import { AddProductComponent } from './add-product.component';
import { FormModule } from 'src/app/_forms/form.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { VitalInformationComponent } from '../vital-information/vital-information.component';
import { AddProductDesciprionComponent } from 'src/app/vendor/variation/add-product-desciprion.component';
import { AddProductVariationComponent } from '../add-product-variation/add-product-variation.component';
import { AddProductMediaComponent } from 'src/app/vendor/add-product-media/add-product-media.component';
import { MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, MatInputModule, MatStepperModule, MatFormFieldModule, MatCheckboxModule, MatCardModule, MatRadioModule, MatSelectModule } from '@angular/material';
import { ExtraDetailsComponent } from '../extra-details/extra-details.component';
import { MetaInfoComponent } from '../meta-info/meta-info.component';


@NgModule({
  imports: [
    AddProductRoutes,
    FormModule,
    NgbModule,

    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatStepperModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatRadioModule,
    MatSelectModule,
    MatIconModule,
    MatCheckboxModule,
    MatToolbarModule
  ],
  bootstrap: [AddProductComponent],
  declarations: [
    AddProductComponent,
    VitalInformationComponent,
    AddProductDesciprionComponent,
    AddProductVariationComponent,
    AddProductMediaComponent,
    ExtraDetailsComponent,
    MetaInfoComponent,
  ],
})
export class AddProductModule {}
