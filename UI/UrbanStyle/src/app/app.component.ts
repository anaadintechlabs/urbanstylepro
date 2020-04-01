import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router, NavigationStart } from '@angular/router';
import { CurrencyService } from './_services/currency.service';

export let Refresh = false;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'UrbanStyle';
  subscription : Subscription

  constructor(
    private _router : Router,
    private currency : CurrencyService
  ){
  this.subscription = this._router.events.subscribe(event=>{
      if(event instanceof NavigationStart){
        Refresh = !this._router.navigated;
      }
    })
  }

  ngOnInit(): void {
    this.currency.options = {
      code: '$',
      display: 'symbol',
      // digitsInfo: '1.2-2',
      locale: 'en-USD'
  };
  }
}
