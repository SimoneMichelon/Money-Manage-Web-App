import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authSubject= new BehaviorSubject<any>({
    user:null
  })

  logout(){
    localStorage.clear();
    this,this.authSubject.next({})
  }
}
