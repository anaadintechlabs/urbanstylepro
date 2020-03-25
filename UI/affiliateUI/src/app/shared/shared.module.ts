import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductViewComponent } from './component/product-view/product-view.component';
import { LogoComponent } from './component/header/logo/logo.component';
import { MenuComponent } from './component/header/menu/menu.component';
import { ActionHandlerComponent } from './component/action-handler/action-handler.component';
import { CurrencyFormatPipe } from './pipe/curruny-formator.ts.pipe';



@NgModule({
  declarations: [
    ActionHandlerComponent,
    ProductViewComponent,
    LogoComponent,
    MenuComponent,
    CurrencyFormatPipe
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
    MenuComponent,
    CurrencyFormatPipe
  ]
})
export class SharedModule { }
