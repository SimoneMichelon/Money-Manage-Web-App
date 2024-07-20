import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {

  constructor(private router: Router) { }

  change : boolean = true;

  ngOnInit(): void {
    if (localStorage.getItem("jwt")) {
      this.router.navigateByUrl('/dashboard');
    }
  }

  isLogin(isLogin : boolean){
    this.change = isLogin;
  }
}
