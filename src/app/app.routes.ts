import { Routes } from '@angular/router';
import { AuthComponent } from './routes/auth/auth.component';
import { DashboardComponent } from './routes/dashboard/dashboard.component';

export const routes: Routes = [
    { path: 'login', component: AuthComponent},
    { path: 'dashboard', component: DashboardComponent},
    { path: '**', component: AuthComponent },


];
