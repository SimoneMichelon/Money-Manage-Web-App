import { Component, EventEmitter, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserControllerService } from '../api/services/user-controller.service';
import { CommonModule } from '@angular/common';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  constructor(private userService: UserControllerService) {}

  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  collapsed = false;
  screenWidth = 0;
  navData = navbarData;

  collapseOff() {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({
      collapsed: this.collapsed,
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
