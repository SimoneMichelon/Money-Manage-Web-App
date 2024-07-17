import { Component } from '@angular/core';
import { SideNavToggle } from './utilities/side-nav-toggle';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  isSideNavCollapsed = false;

  collapsedOff(data: SideNavToggle): void {
    this.isSideNavCollapsed = data.collapsed;
  }

  isLogged(){
    return true;
  }
}
