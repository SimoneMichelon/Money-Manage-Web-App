import { Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { VaultControllerService } from '../../../api/services';

@Component({
  selector: 'app-delete-vault-dialog',
  templateUrl: './delete-vault-dialog.component.html',
  styleUrl: './delete-vault-dialog.component.scss'
})
export class DeleteVaultDialogComponent {
  data: any = inject<number>(MAT_DIALOG_DATA);



  constructor(private vaultControllerService : VaultControllerService,
    private dialogRef : MatDialogRef<DeleteVaultDialogComponent>
  ){}

  deleteVault(){
    let id = this.data.vault.id;
    this.vaultControllerService.deleteVaultById({id : id}).subscribe({
      next : (response) => {
        console.log("Vault Deleted !");
        this.dialogRef.close({delete : true})
      },
      error : (error) => {
        console.log("Error : ", error);
      }
    })
  }
}
