import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UserDto, VaultDto } from '../../../api/models';
import { UserControllerService, VaultControllerService } from '../../../api/services';

@Component({
  selector: 'app-vault-dialog',
  templateUrl: './vault-dialog.component.html',
  styleUrl: './vault-dialog.component.scss'
})
export class VaultDialogComponent implements OnInit{

  constructor(private userControllerService : UserControllerService,
    private vaultControllerService : VaultControllerService,
    private dialogRef : MatDialogRef<VaultDialogComponent>

  ){}

  ngOnInit(): void {
   this.getUser();
  }

  principal! : UserDto;

  vaultForm = new FormGroup({
      name: new FormControl("", [Validators.minLength(3), Validators.required]),
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
        this.dialogRef.close({delete : true})
      },
      error : (error) => {
        console.log("Error : ", error);
      }
    })
  }

  getUser() {
    this.userControllerService.getUserByJwt().subscribe({
      next: (response) => {
        this.principal = response;
      },
      error: (error) => {
        console.log('Error : ', error);
      },
    });
  }
}
