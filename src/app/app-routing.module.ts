import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { authGuard } from './security/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: AuthComponent},
  { path: 'dashboard', component: MainPageComponent, canActivate: [authGuard],
    children: [
      { path: 'user-profile', component : UserProfileComponent}
    ]
  },
  
  { path: '**', redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

  
}
