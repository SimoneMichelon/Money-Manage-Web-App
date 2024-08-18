import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CredentialDto, LoginRequest } from '../../api/models';
import { AuthControllerService } from '../../api/services';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.scss'
})
export class AuthenticationComponent implements OnInit{

  ngOnInit(): void {
    this.firstOrder();
  }

  constructor(private authControllerService : AuthControllerService,
    private router: Router){}

  login : boolean = true;
  isFocus : number | null = null;
  isBlur: number | null = null;
  isVisible : boolean = false;
  counter : number = 0;

  
  loginForm = new FormGroup({
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  });

  registrationForm = new FormGroup({
    firstName : new FormControl("",[Validators.minLength(3), Validators.required]),
    lastName : new FormControl("",[Validators.minLength(3), Validators.required]),
    dob : new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.email, Validators.minLength(10), Validators.required]),
    password: new FormControl("", [Validators.minLength(8), Validators.required])
  })

  registerHandler(){
    if(this.registrationForm.invalid == true){
      this.errorHandler();
      return;
    }

    let registerRequest : CredentialDto = {
      email : this.registrationForm.value.email!,
      password : this.registrationForm.value.password!,
      userDTO : {
        firstName : this.registrationForm.value.firstName!,
        lastName : this.registrationForm.value.lastName!,
        dob : this.registrationForm.value.dob!,
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
      }
    });
  }

  errorHandler(){

  }

  changeMethod(){
    this.login = !this.login;

    let registration = document.getElementById("registration") as HTMLElement;
    let login = document.getElementById("login") as HTMLElement;
    let routeElements = document.querySelectorAll(".route");

    routeElements.forEach((element) => {
      element.classList.add("not-clickable");
    });

    setTimeout(() => {
      routeElements.forEach((element) => {
        element.classList.remove("not-clickable");
      });
    }, 1000);
    

    if(this.counter == 0){
      this.counter++;
      setTimeout(() => {
        registration.classList.remove("not-active");
      }, 1000);
    }

    if(this.login == true){
      registration.classList.remove("slideIn");
      registration.classList.add("slideOut");
      // login.classList.remove("slideOut");
      setTimeout(() => {
        login.classList.remove("slideOut");
        login.classList.add("slideIn");
      }, 1000);

    }
    else {
      login.classList.remove("slideIn");
      login.classList.add("slideOut");
      setTimeout(() => {
        registration.classList.remove("slideOut");
        registration.classList.add("slideIn");
      }, 1000);
    }
  }

  firstOrder(){
    let registration = document.getElementById("registration") as HTMLElement;
    registration.classList.add("not-active");
  }

  focus(inputNumber: number) {
    this.isFocus = inputNumber;
    this.isBlur = null; 
  }

  blur(inputNumber: number) {
    this.isBlur = inputNumber;
    this.isFocus = null;
  }
  
  changeVisibility(){
    this.isVisible = !this.isVisible;
    if(this.login == false){
      let passwordInput = document.getElementById("password")  as HTMLInputElement;

      if (passwordInput.type === "password") {
        passwordInput.type = "text";
      } else {
        passwordInput.type = "password";
      }
    }
    else{
      let passwordInput = document.getElementById("password-login")  as HTMLInputElement;

      if (passwordInput.type === "password") {
        passwordInput.type = "text";
      } else {
        passwordInput.type = "password";
      }
    }
  }


}
