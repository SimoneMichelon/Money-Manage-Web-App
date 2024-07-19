import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { StatisticsComponent } from './pages/statistics/statistics.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { authGuard } from './security/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: AuthComponent },
  {
    path: 'dashboard',
    component: MainPageComponent,
    canActivate: [authGuard],
    children: [
      { path: 'statistics', component: StatisticsComponent },
      { path: 'user-profile', component : UserProfileComponent}],
  },
  { path: '**', redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
