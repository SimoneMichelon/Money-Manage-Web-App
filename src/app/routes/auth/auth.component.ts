import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { LoginRequest } from '../../api/models';
import { AuthControllerService } from '../../api/services';


@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [ReactiveFormsModule, 
    MatSelectModule, 
    MatInputModule, 
    MatFormFieldModule,
    MatIconModule,
    MatDividerModule,
    MatButtonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
  constructor(private authService : AuthControllerService){}

  loginForm = new FormGroup({
    email : new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password : new FormControl("", [Validators.minLength(8), Validators.required])
  });

  loginHandler(){
    let loginData : LoginRequest = 
    {
      email : this.loginForm.value.email!,
      password : this.loginForm.value.password!
    }


    this.authService.signIn({
      body : loginData
    }).subscribe({
      next: (response) => {
        localStorage.setItem("jwt", response.jwt!);
      },
      error : (response) => {
        console.log(response.status)
      }
    });
  }
}
