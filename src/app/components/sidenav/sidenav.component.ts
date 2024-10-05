import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';
import { LogoutDialogComponent } from '../logout-dialog/logout-dialog.component';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SideNavComponent implements OnInit{
  navData = sideNavData;

  previousRoute : string | null = null;

  logoutData: any = {
    icon: 'material-symbols-outlined',
    label: 'Logout',
    iconName: 'exit_to_app',
  };

  constructor(
    private userControllerService: UserControllerService,
    private authService : AuthService,
    private dialog: MatDialog,
    private router : Router
  ) {}

  ngOnInit(): void {
    this.routeOnFirstAccess();
  }

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

  openLogoutDialog(): void {
    let logoutRoute = document.getElementById("logout");
    logoutRoute?.classList.add("logout-active");

    this.dialog.open(LogoutDialogComponent, {
      width:'fit-content',
      height:'fit-content',
      autoFocus : true
      
    }).afterClosed().subscribe({
      next: (response) => {
        logoutRoute?.classList.remove("logout-active");
        if (response) {
          this.logout();
        }
      }
    });
  }

  async routeSelector(label : any){
    let routes =await  document.querySelectorAll(".bridge");
    routes.forEach((x) => {
      x.classList.remove("selected");
    })
    
    let route = document.getElementById(label);
    route?.classList.add("selected")

    this.previousRoute = label;
  }

  routeOnFirstAccess(){
    if(this.previousRoute == null){
      let fetch = this.router.url.split("/");
      let id_nav = fetch.at(fetch.length-1);
      this.routeSelector(id_nav);
    }
  }
}

const sideNavData = [
  {
    routeLink: 'dashboard',
    icon: 'material-symbols-outlined',
    label: 'Dashboard',
    iconName: 'home_app_logo'
  },
  {
    routeLink: 'activities',
    icon: 'material-symbols-outlined',
    label: 'Activities',
    iconName: 'Monitoring'
  },
  {
    routeLink: 'vaults',
    icon: 'material-symbols-outlined',
    label: 'Cards',
    iconName: 'credit_card'
  },
  {
    routeLink: 'user-profile',
    icon: 'material-symbols-outlined',
    label: 'Profile', 
    iconName: 'account_circle'
  },
  {
    routeLink: 'settings',
    icon: 'material-symbols-outlined',
    label: 'Settings',
    iconName: 'settings'
  }
];
