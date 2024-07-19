import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import {
  HttpClientModule,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SideNavComponent } from './components/sidenav/sidenav.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { headersInterceptor } from './security/headers.interceptor';



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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterOutlet,
    FormsModule
  ],
  providers: [provideHttpClient(withInterceptors([headersInterceptor]))],
  bootstrap: [AppComponent],
})
export class AppModule {}
