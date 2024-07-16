import { Routes } from '@angular/router';
import { AuthComponent } from './routes/auth/auth.component';
import { DashboardComponent } from './routes/dashboard/dashboard.component';
import { NotFoundComponent } from './routes/not-found/not-found.component';
import { authGuard } from './security/auth-guard.service';

export const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'login', component: AuthComponent},
    { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
    { path: '**', component: NotFoundComponent },
];
