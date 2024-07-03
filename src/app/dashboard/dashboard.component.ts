import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserControllerService } from '../api/services/user-controller.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private userService: UserControllerService) { }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(users => {
      console.log(users);
    });
  }
}