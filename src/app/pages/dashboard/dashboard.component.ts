import { Component, HostListener, OnInit } from '@angular/core';
import { VaultDto } from '../../api/models';
import { VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit{

  constructor(private vaultControllerService : VaultControllerService,
    private authService: AuthService,
  ){}

  selected: VaultDto | null = null;
  isOpen = false;

  ngOnInit(): void {
    this.getVaultsByPrincipal();
  }
  vaults!: Array<VaultDto>;


  getVaultsByPrincipal() {
     this.vaultControllerService.getAllVaultsByPrincipal().subscribe({
      next: (response) => {
        this.vaults = response;

        if (this.vaults.length > 0) {
          this.selected = this.vaults[0];
        }
      },
      error: () => {
        this.authService.logout();
      }
    })
  };

  
  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false; 
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.custom-select')) {
      this.isOpen = false;
    }
  }

}