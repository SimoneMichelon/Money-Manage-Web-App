import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../../../api/models/login-request';
import { AuthControllerService } from '../../../api/services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(private authControllerService: AuthControllerService,
    private router: Router) { }

  @Output() isLogin = new EventEmitter<boolean>();

  loginForm = new FormGroup({
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  });

  loginHandler() {
    let loginData: LoginRequest =
    {
      email: this.loginForm.value.email!,
      password: this.loginForm.value.password!
    }

    this.authControllerService.signIn({
      body: loginData
    }).subscribe({
      next: (response) => {
        localStorage.setItem("jwt", response.jwt!);
        this.router.navigateByUrl('/dashboard');
      },
      error: (error) => {
        console.log(error)
      }
    });
  }

  switch(){
    this.isLogin.emit(false);
  }
}
