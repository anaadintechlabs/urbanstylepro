import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router, NavigationStart } from '@angular/router';

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
    private _router : Router
  ){
  this.subscription = this._router.events.subscribe(event=>{
      if(event instanceof NavigationStart){
        Refresh = !this._router.navigated;
      }
    })
  }
}
