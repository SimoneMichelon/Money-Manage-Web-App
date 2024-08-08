import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDto, VaultDto } from '../../../api/models';
import { UserControllerService, VaultControllerService } from '../../../api/services';

@Component({
  selector: 'app-edit-vault-dialog',
  templateUrl: './edit-vault-dialog.component.html',
  styleUrl: './edit-vault-dialog.component.scss'
})
export class EditVaultDialogComponent {

  constructor(private userControllerService : UserControllerService,
    private vaultControllerService : VaultControllerService
  ){}

  data : any = inject<number>(MAT_DIALOG_DATA);

  vaultDto! : VaultDto ;

  ngOnInit(): void {
   this.getVaultById();
  }

  principal! : UserDto;

  vaultForm = new FormGroup({
      name: new FormControl("",[Validators.minLength(3), Validators.required]),
      capital: new FormControl(0,[Validators.min(0) ,Validators.required])
    }
  )

  createVault(){
    let data : VaultDto = {
      id : 0,
      name : this.vaultForm.value.name!,
      userDTO : this.principal,
      capital : this.vaultForm.value.capital!

    }
    this.vaultControllerService.createVault({body : data}).subscribe({
      next :(response) => {
        console.log("Vault Created !");
      },
      error : (error) => {
        console.log("Error : ", error);
      }
    })
  }

  getVaultById(){

    let id = this.data.vault_id;
    console.log(id)

    this.vaultControllerService.getVaultById({id : id}).subscribe({
      next : (response) => {
        console.log(response);
        this.vaultDto = response;
      },
      error : (error) => {
        console.log("Error : ", error);
      }
    })
  }
}
