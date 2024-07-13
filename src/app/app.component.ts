import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthComponent } from './routes/auth/auth.component';
import { DashboardComponent } from './routes/dashboard/dashboard.component';

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterOutlet, DashboardComponent, AuthComponent],
})
export class AppComponent {
  isSideNavCollapsed = false;
  screenWidth = 0;

  collapsedOff(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }
}
