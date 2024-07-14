import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CommonModule } from '@angular/common';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterOutlet, DashboardComponent, CommonModule],
})
export class AppComponent {
  isSideNavCollapsed = false;

  collapsedOff(data: SideNavToggle): void {
    this.isSideNavCollapsed = data.collapsed;
  }
}
