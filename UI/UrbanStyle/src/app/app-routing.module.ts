import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserGuardGuard } from 'src/_services/http_&_login/user-guard.guard';


const routes: Routes = [
  {
    path : '',
    redirectTo : 'vendor',
    pathMatch : 'full'
    // loadChildren: () => import('./customer/customer.module').then(m=> m.CustomerModule),
    // canActivate : [UserGuardGuard]
  },
  {
    path : 'vendor',
    loadChildren: './vendor/vendor.module#VendorModule',
    canActivate : [UserGuardGuard]
  },
  {
    path : 'edit/:productID/:variantID',
    loadChildren : () => import("./edit-product/edit-product.module").then(m => m.EditProductModule)
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
