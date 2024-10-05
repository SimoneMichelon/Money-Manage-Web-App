import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDto } from '../../api/models';
import { UserControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit{
  constructor(
    private userControllerService : UserControllerService,
    private authService : AuthService
  ){}

  principal : UserDto = {};


  ngOnInit(): void {
    this.getPrincipal().subscribe({
      next: (data) => {
        this.principal = data;
      },
      error: (error) => {
        console.log('Error:', error);
      }
    });
  }

  getPrincipal(): Observable<UserDto> {
    return new Observable<UserDto>((observer) => {
      this.userControllerService.getUserByJwt().subscribe({
        next: (response) => {
          observer.next(response);
          observer.complete();
        },
        error: (error) => {
          this.authService.logout();
          observer.error(error);
        }
      });
    });
  }

  // loadUserProfile(): void {
  //   // Simulate an API call to load user profile
  // }

  // onSubmit(): void {
  //   // Handle form submission to save changes
  //   console.log('Profile updated:', this.principal);
  //   alert('Profile updated successfully!');
  // }
}
