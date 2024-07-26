import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


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
import { LoginComponent } from './components/login/login.component';
import { LogoutDialogComponent } from './components/logout-dialog/logout-dialog.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { SideNavComponent } from './components/sidenav/sidenav.component';
import { AuthComponent } from './pages/auth/auth.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { headersInterceptor } from './security/headers.interceptor';
import { VaultsComponent } from './pages/vaults/vaults.component';




@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    NotFoundComponent,
    SideNavComponent,
    MainPageComponent,
    UserProfileComponent,
    LoginComponent,
    RegisterComponent,
    LogoutDialogComponent,
    VaultsComponent,
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
  ],
  providers: [provideHttpClient(withInterceptors([headersInterceptor])), provideAnimationsAsync()],
  bootstrap: [AppComponent],
})
export class AppModule {}
