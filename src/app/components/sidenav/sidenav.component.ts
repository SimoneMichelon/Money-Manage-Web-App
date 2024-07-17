import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { UserControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.scss'
})
export class SideNavComponent {
  constructor(
    private userControllerService: UserControllerService,
    private authService : AuthService,
    private router: Router
  ) { }

  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  collapsed = false;
  screenWidth = 0;
  navData = navbarData;

  logoutData : any =   {
    icon: 'material-symbols-outlined',
    label: 'Logout', 
    iconName: 'Logout',
  };


  collapseOff() {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({
      collapsed: this.collapsed,
    });
  }

  logout() {
    this.authService.logout();
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
    iconName: 'Home',
  },
  {
    routeLink: 'scheduledOperations',
    icon: 'material-symbols-outlined',
    label: 'Vault Account',
    iconName: 'event_upcoming',
  },
  {
    routeLink: 'scheduledOperations',
    icon: 'material-symbols-outlined',
    label: 'Scheduled Operations',
    iconName: 'event_upcoming',
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
    routeLink: 'chartsData',
    icon: 'material-symbols-outlined',
    label: 'Charts Data',
    iconName: 'chart_data',
  },
  {
    routeLink: 'scheduledOperations',
    icon: 'material-symbols-outlined',
    label: 'Scheduled Operations',
    iconName: 'event_upcoming',
  },
];