import { Component, OnInit } from '@angular/core';
import { VaultDto } from '../../api/models';
import { VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-vaults',
  templateUrl: './vaults.component.html',
  styleUrl: './vaults.component.scss'
})
export class VaultsComponent implements OnInit {

  vaults?: Array<VaultDto>;

  constructor(private vaultControllerService: VaultControllerService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getVaultsByPrincipal();
  }

  getVaultsByPrincipal() {
    this.vaultControllerService.getAllVaultsByPrincipal().subscribe({
      next: (response) => {
        this.vaults = response;
      },
      error: () => {
        this.authService.logout();
      }
    })
  };

  isEmpty(): boolean{
    return this.vaults?.length == 0;
  }


}
