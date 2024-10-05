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
      width: "fit-content",
      height: "fit-content",
      autoFocus: false
    }).afterClosed().subscribe({
      next : (result) => {
       if(result.delete)
         this.getVaultsByPrincipal();
       }
    });
  }

  openEditDialog(id : number, name : string): void {
    let editButton = document.getElementById(name);
    editButton?.classList.add("selected");

    this.dialog.open(EditVaultDialogComponent, {
      data : {vault_id : id},
      width: "fit-content",
      height: "fit-content",
      autoFocus: false
    }).afterClosed().subscribe({
      next : (result) => {
        editButton?.classList.remove("selected");
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
     next : () => {
      this.getVaultsByPrincipal();
      }
    });
  }

  goToActivities(){

  }

}
