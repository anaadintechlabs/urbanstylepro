import { Routes } from '@angular/router';
import { WelcomeScreenComponent } from './pages/welcome-screen/welcome-screen.component';
import { AuthGuardService } from 'src/_services/http_&_login/authGuard.service';

export const AffiliateRoutes: Routes=[
    {
        path : 'dashboard',
        loadChildren : () => import('./pages/dashboard/dashboard.module').then(m=> m.DashboardModule),
        canActivate : [AuthGuardService],
    }
];