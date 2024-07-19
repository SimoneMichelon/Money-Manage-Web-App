import { Component } from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent {
  isBlurred = false;

  onToggleSideNav(event: { collapsed: boolean }) {
    this.isBlurred = event.collapsed; 
    console.log('Content blurred:', this.isBlurred);
  }
}
