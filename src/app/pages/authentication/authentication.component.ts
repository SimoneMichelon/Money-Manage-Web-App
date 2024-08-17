import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.scss'
})
export class AuthenticationComponent implements OnInit{

  ngOnInit(): void {
  }

  login : boolean = false;
  isFocus : number | null = null;
  isBlur: number | null = null;

  inputField = document.getElementsByName("input") ;
  fullInput = document.getElementsByClassName("full-input");

  registrationForm = new FormGroup({
    firstName : new FormControl("",[Validators.minLength(3), Validators.required]),
    lastName : new FormControl("",[Validators.minLength(3), Validators.required]),
    dob : new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  })

  changeMethod(){
    this.login = !this.login;
  }

  focus(inputNumber: number) {
    this.isFocus = inputNumber;
    this.isBlur = null; 
  }

  blur(inputNumber: number) {
    this.isBlur = inputNumber;
    this.isFocus = null;
  }
  

}
