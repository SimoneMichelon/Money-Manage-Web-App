import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ThirdPartyDto } from '../../api/models';
import { ThirdPartyControllerService } from '../../api/services';

@Component({
  selector: 'app-third-party-dialog',
  templateUrl: './third-party-dialog.component.html',
  styleUrl: './third-party-dialog.component.scss'
})
export class ThirdPartyDialogComponent {
  constructor(private thirdPartyControllerService: ThirdPartyControllerService,
    private dialogRef: MatDialogRef<ThirdPartyDialogComponent>
  ) { }

  ngOnInit(): void {

  }

  itemForm = new FormGroup({
    name: new FormControl("", [Validators.minLength(3), Validators.required])
  }
  )

  createItem() {
    let data: ThirdPartyDto = {
      id: 0,
      thirdPartyName: this.itemForm.value.name!,
    }
    this.thirdPartyControllerService.createThirdParty({ body: data }).subscribe({
      next: (response) => {
        console.log("ThirdParty Created !");
        this.dialogRef.close()
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    })
  }
}
