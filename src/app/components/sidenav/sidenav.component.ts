import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SideNavComponent {
  navData = navbarData;

  logoutData: any = {
    icon: 'material-symbols-outlined',
    label: 'Logout',
    iconName: 'Logout',
  };

  constructor(
    private userControllerService: UserControllerService,
    private authService: AuthService,
    private router: Router
  ) {}

  logout() {
    this.authService.logout();
  }

  getUser() {
    this.userControllerService.getUserByJwt().subscribe({
      next: (response) => {
        console.log('Response {}', response);
      },
      error: (error) => {
        console.log('Error {}', error);
      },
    });
  }
}

const navbarData = [
  {
    routeLink: 'dashboard',
    icon: 'material-symbols-outlined',
    label: 'Dashboard',
    iconName: 'Home',
  },
  {
    routeLink: 'user-profile',
    icon: 'material-symbols-outlined',
    label: 'Profile', 
    iconName: 'adb',
  },
  {
    routeLink: 'scheduledOperations',
    icon: 'material-symbols-outlined',
    label: 'Vaults',
    iconName: 'savings',
  },
  {
    routeLink: 'statistics',
    icon: 'material-symbols-outlined',
    label: 'Statistics',
    iconName: 'Monitoring',
  },
  {
    routeLink: 'charts',
    icon: 'material-symbols-outlined',
    label: 'Charts',
    iconName: 'bar_chart',
  },
  {
    routeLink: 'scheduledOperations',
    icon: 'material-symbols-outlined',
    label: 'Operations',
    iconName: 'event_upcoming',
  },
];
