import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerForm = new FormGroup({
    firstName : new FormControl("",[Validators.minLength(3), Validators.required]),
    lastName : new FormControl("",[Validators.minLength(3), Validators.required]),
    dob : new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  })

  registerHandler(){
    
  }
}
