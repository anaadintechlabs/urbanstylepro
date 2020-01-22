import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserGuardGuard } from 'src/_services/http_&_login/user-guard.guard';


const routes: Routes = [
  {
    path : '',
    loadChildren: () => import('./customer/customer.module').then(m=> m.CustomerModule),
    // canActivate : [UserGuardGuard]
  },
  {
    path : 'vendor',
    loadChildren: './vendor/vendor.module#VendorModule',
    canActivate : [UserGuardGuard]
  },
  {
    path : 'affiliate',
    loadChildren: () => import('./affiliate/affiliate.module').then(m=> m.AffiliateModule),
    canActivate : [UserGuardGuard]
  },
  {
    path:'admin',
    loadChildren: () => import("./admin/admin.module").then(m=> m.AdminModule),
    canActivate : [UserGuardGuard]
  },
  {
    path : 'account',
    loadChildren : () => import("./account/account.module").then(m => m.AccountModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
