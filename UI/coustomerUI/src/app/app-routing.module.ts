import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeOneComponent } from './pages/home-one/home-one.component';
import { RootComponent } from './components/root/root.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { AuthGuardService } from 'src/_service/http_&_login/authGuard.service';


const routes: Routes = [
  {
      path: '',
      component: RootComponent,
      data: {
          headerLayout: 'classic'
      },
      children: [
          {
              path: '',
              pathMatch: 'full',
              redirectTo: 'home'
          },
          {
              path: 'home',
              component: HomeOneComponent
          },
          {
            path: ':id',
            loadChildren: () => import('./single-product-view/single-product-view.module').then(m => m.SingleProductViewModule)
          },
          // {
          //     path: 'blog',
          //     loadChildren: () => import('./modules/blog/blog.module').then(m => m.BlogModule)
          // },
          {
              path: 'shop',
              loadChildren: () => import('./modules/shop/shop.module').then(m => m.ShopModule)
          },
          {
              path: 'account',
              loadChildren: () => import('./modules/account/account.module').then(m => m.AccountModule)
          },
          {
            path: 'order/:id',
            canActivateChild : [AuthGuardService],
            loadChildren: () => import('./modules/order/order.module').then(m => m.OrderModule)
          },
          {
            path: 'order',
            canActivateChild : [AuthGuardService],
            loadChildren: () => import('./modules/order/order.module').then(m => m.OrderModule)
          },
          // {
          //     path: 'site',
          //     loadChildren: () => import('./modules/site/site.module').then(m => m.SiteModule)
          // },
          {
              path: '**',
              component: PageNotFoundComponent
          }
      ],
  },
  {
      path: '**',
      redirectTo: 'classic'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
