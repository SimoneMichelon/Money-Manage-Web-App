import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserDto, VaultDto } from '../../../api/models';
import { VaultControllerService } from '../../../api/services';

@Component({
  selector: 'app-edit-vault-dialog',
  templateUrl: './edit-vault-dialog.component.html',
  styleUrls: ['./edit-vault-dialog.component.scss']
})
export class EditVaultDialogComponent implements OnInit {

  constructor(
    private vaultControllerService: VaultControllerService,
    private dialogRef : MatDialogRef<EditVaultDialogComponent>
  ) {}

  data: any = inject<number>(MAT_DIALOG_DATA);

  vaultDto!: VaultDto;

  vaultForm!: FormGroup;

  principal!: UserDto;

  ngOnInit(): void {
    this.getVaultById();
  }

  editVault() {
    if (this.vaultForm.valid) {
      let data: VaultDto = {
        id: this.vaultDto.id,
        name: this.vaultForm.value.name!,
        userDTO: this.vaultDto.userDTO,
        capital: this.vaultForm.value.capital!
      };

      this.vaultControllerService.updateVault({ body: data }).subscribe({
        next: (response) => {
          console.log('Vault Edited!');
          this.dialogRef.close({delete : true});
        },
        error: (error) => {
          console.log('Error:', error);
        }
      });
    }
  }

  getVaultById() {
    let id = this.data.vault_id;
    this.vaultControllerService.getVaultById({ id: id }).subscribe({
      next: (response) => {
        this.vaultDto = response;

        this.vaultForm = new FormGroup({
          name: new FormControl(this.vaultDto.name, [Validators.minLength(3), Validators.required]),
          capital: new FormControl(this.vaultDto.capital, [Validators.min(0), Validators.required])
        });
      },
      error: (error) => {
        console.log('Error:', error);
      }
    });
  }
}
