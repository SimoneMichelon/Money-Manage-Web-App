import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { UserControllerService } from '../../api/services';

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  constructor(private userControllerService: UserControllerService,
    private router: Router
  ) { }

  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  collapsed = false;
  screenWidth = 0;
  navData = navbarData;

  collapseOff() {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({
      collapsed: this.collapsed,
      screenWidth: this.screenWidth,
    });
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl('/login');
  }

  getUser(){

    this.userControllerService.getUserByJwt().subscribe({
      next : (response) =>{
        console.log("Response {}",response);
      },
      error : (error) => {
        console.log("Error {}", error)
      }
    })
  }
}

const navbarData = [
  {
    routeLink: 'dashboard',
    icon: 'material-symbols-outlined',
    label: 'Dashboard',
  }
];