import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';
import { LogoutDialogComponent } from './logout-dialog/logout-dialog.component';

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
    private authService : AuthService,
    private dialog: MatDialog
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

  openDialog(): void {
    this.dialog.open(LogoutDialogComponent, {
      width: '250px'
    }).afterClosed().subscribe({
      next: (response) => {
        if (response) {
          this.logout();
        }
      }
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
