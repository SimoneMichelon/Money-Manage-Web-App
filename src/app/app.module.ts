import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';


import {
    HttpClientModule,
    provideHttpClient,
    withInterceptors,
} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle
} from '@angular/material/dialog';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { RouterOutlet } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogoutDialogComponent } from './components/logout-dialog/logout-dialog.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SideNavComponent } from './components/sidenav/sidenav.component';
import { ActivitiesComponent } from './pages/activities/activities.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { DeleteVaultDialogComponent } from './pages/vaults/delete-vault-dialog/delete-vault-dialog.component';
import { EditVaultDialogComponent } from './pages/vaults/edit-vault-dialog/edit-vault-dialog.component';
import { VaultDialogComponent } from './pages/vaults/vault-dialog/vault-dialog.component';
import { VaultsComponent } from './pages/vaults/vaults.component';
import { headersInterceptor } from './security/headers.interceptor';




@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    SideNavComponent,
    MainPageComponent,
    UserProfileComponent,
    LogoutDialogComponent,
    VaultsComponent,
    VaultDialogComponent,
    EditVaultDialogComponent,
    DeleteVaultDialogComponent,
    AuthenticationComponent,
    ActivitiesComponent,
    SettingsComponent,
    DashboardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterOutlet,
    FormsModule,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatButtonModule,
    MatDividerModule,
    MatIconModule
  ],
  providers: [provideHttpClient(withInterceptors([headersInterceptor])), provideAnimationsAsync()],
  bootstrap: [AppComponent],
})
export class AppModule {}
