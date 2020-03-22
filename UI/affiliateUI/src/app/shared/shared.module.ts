import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductViewComponent } from './component/product-view/product-view.component';
import { LogoComponent } from './component/header/logo/logo.component';
import { MenuComponent } from './component/header/menu/menu.component';
import { ActionHandlerComponent } from './component/action-handler/action-handler.component';



@NgModule({
  declarations: [
    ActionHandlerComponent,
    ProductViewComponent,
    LogoComponent,
    MenuComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports : [
    ActionHandlerComponent,
    ProductViewComponent,
    LogoComponent,
    MenuComponent
  ]
})
export class SharedModule { }
