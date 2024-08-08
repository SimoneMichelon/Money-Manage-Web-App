import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { VaultDto } from '../../api/models';
import { VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';
import { DeleteVaultDialogComponent } from './delete-vault-dialog/delete-vault-dialog.component';
import { EditVaultDialogComponent } from './edit-vault-dialog/edit-vault-dialog.component';
import { VaultDialogComponent } from './vault-dialog/vault-dialog.component';



@Component({
  selector: 'app-vaults',
  templateUrl: './vaults.component.html',
  styleUrl: './vaults.component.scss'
})
export class VaultsComponent implements OnInit {

  vaults?: Array<VaultDto>;

  constructor(private vaultControllerService: VaultControllerService,
    private authService: AuthService,
    private dialog: MatDialog
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

  openCreateDialog(): void {
    this.dialog.open(VaultDialogComponent, {
      width: "30%",
      height: "auto"
    }).afterClosed().subscribe({
      next : (result) => {
       if(result.delete)
         this.getVaultsByPrincipal();
       }
    });
  }

  openEditDialog(id : number): void {
    this.dialog.open(EditVaultDialogComponent, {
      data : {vault_id : id},
      width: "30%",
      height: "auto"
    }).afterClosed().subscribe({
      next : (result) => {
       if(result.delete)
         this.getVaultsByPrincipal();
       }
    });
  }

  openDeleteDialog(vault : VaultDto): void {
    this.dialog.open(DeleteVaultDialogComponent, {
      data : {vault : vault},
      width: "auto",
      height: "auto"
    }).afterClosed().subscribe({
     next : (result) => {
      if(result.delete)
        this.getVaultsByPrincipal();
      }
    });
  }

}
