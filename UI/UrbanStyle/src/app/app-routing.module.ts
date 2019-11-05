import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path : '',
    loadChildren: () => import('./customer/customer.module').then(m=> m.CustomerModule),
  },
  {
    path : 'vendor',
    loadChildren: () => import('./vendor/vendor.module').then(m=> m.VendorModule),
  },
  {
    path : 'affiliate',
    loadChildren: () => import('./affiliate/affiliate.module').then(m=> m.AffiliateModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
