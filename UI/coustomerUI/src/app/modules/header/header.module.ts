import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';
import { MenuComponent } from './components/menu/menu.component';
import { RightMenuComponent } from './components/right-menu/right-menu.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { TopBarSearchComponent } from './components/top-bar-search/top-bar-search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HeaderComponent, 
    MenuComponent, 
    RightMenuComponent, TopBarSearchComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports : [
    HeaderComponent
  ]
})
export class HeaderModule { }
