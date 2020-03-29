import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RootComponent } from './components/root/root.component';
import { HomeOneComponent } from './pages/home-one/home-one.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { HeaderModule } from './modules/header/header.module';
import { FooterModule } from './modules/footer/footer.module';
import { HttpClientModule } from '@angular/common/http';
import { BlockModule } from './modules/blocks/block.module';
import { JwtServiceService } from 'src/_service/http_&_login/jwt-service.service';
import { CartSideMenuComponent } from './modules/header/components/cart-side-menu/cart-side-menu.component';
// import { CurrencyFormatPipe } from './shared/pipes/curruncy-formator';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent,
    RootComponent,
    HomeOneComponent,
    PageNotFoundComponent,
    CartSideMenuComponent,
    // CurrencyFormatPipe,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule ,
    ToastrModule.forRoot(),
    AppRoutingModule,
    HeaderModule,
    FooterModule,
    HttpClientModule,
    BlockModule,
    FormsModule, 
    ReactiveFormsModule,
    SharedModule
  ],
  providers: [
    JwtServiceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
