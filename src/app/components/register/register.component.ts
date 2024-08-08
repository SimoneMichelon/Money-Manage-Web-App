import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CredentialDto } from '../../api/models';
import { AuthControllerService } from '../../api/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  constructor(private authControllerService : AuthControllerService,
    private router: Router){}

  @Output() isLogin = new EventEmitter<boolean>();

  registerForm = new FormGroup({
    firstName : new FormControl("",[Validators.minLength(3), Validators.required]),
    lastName : new FormControl("",[Validators.minLength(3), Validators.required]),
    dob : new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  })

  registerHandler(){
    let registerRequest : CredentialDto = {
      email : this.registerForm.value.email!,
      password : this.registerForm.value.password!,
      userDTO : {
        firstName : this.registerForm.value.firstName!,
        lastName : this.registerForm.value.lastName!,
        dob : this.registerForm.value.dob!,
        imgProfile : "",
        role: "GUEST"
      }
    };

    this.authControllerService.signUp({body : registerRequest}).subscribe({
      next : (response) =>{
        localStorage.setItem("jwt", response.jwt!);
        this.router.navigateByUrl('/dashboard');
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  switch(){
    this.isLogin.emit(true);
  }
}
