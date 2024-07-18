import { Component } from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss',
})
export class MainPageComponent {
  isBlurred = false; 

  onToggleSideNav(event: { collapsed: boolean }) {
    this.isBlurred = !event.collapsed;
  }
}
