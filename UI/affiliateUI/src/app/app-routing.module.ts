import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './affiliate/pages/main/main.component';
import { WelcomeScreenComponent } from './affiliate/pages/welcome-screen/welcome-screen.component';


const routes: Routes = [
  {
    path : "",
    component : MainComponent,
    children : [
      {
        path : '',
        component : WelcomeScreenComponent
      },
      {
        path : 'affiliate',
        loadChildren: () => import('./affiliate/affiliate.module').then(m=> m.AffiliateModule)
      },
    ]
  },
  {
    path : 'account',
    loadChildren : () => import("./account/account.module").then(m => m.AccountModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
