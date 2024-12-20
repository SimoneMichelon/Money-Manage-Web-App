import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ActivitiesComponent } from './pages/activities/activities.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { VaultsComponent } from './pages/vaults/vaults.component';
import { authGuard } from './security/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: AuthenticationComponent },
  {
    path: 'it', component: MainPageComponent, canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'prefix'},
      { path: 'dashboard', component: DashboardComponent},
      { path: 'activities', component: ActivitiesComponent},
      { path: 'vaults', component: VaultsComponent },
      { path: 'user-profile', component : UserProfileComponent},
      { path: 'settings', component: SettingsComponent},
    ],
  },
  { path: '**', redirectTo: '/it', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
