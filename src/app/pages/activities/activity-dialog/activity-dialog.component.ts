import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UserDto } from '../../../api/models';
import { ExpenseControllerService, RevenueControllerService, UserControllerService } from '../../../api/services';

@Component({
  selector: 'app-activity-dialog',
  templateUrl: './activity-dialog.component.html',
  styleUrl: './activity-dialog.component.scss'
})
export class ActivityDialogComponent implements OnInit{

    constructor(private userControllerService : UserControllerService,
      private expenseControllerService : ExpenseControllerService,
      private revenueControllerService : RevenueControllerService,
      private dialogRef : MatDialogRef<ActivityDialogComponent>
    ){}
  
    ngOnInit(): void {
    //  this.getUser();
    }
  
    principal! : UserDto;
  
    activityForm = new FormGroup({
      causal : new FormControl("", [Validators.minLength(3), Validators.required]),
      amount : new FormControl(0.00, [Validators.min(0), Validators.required]),
      category : new FormControl("", [Validators.minLength(3), Validators.required]),
      thirdPartys : new FormControl("", [Validators.minLength(3), Validators.required]),
      vault : new FormControl("", [Validators.minLength(3), Validators.required]),
      type : new FormControl("", [Validators.minLength(3), Validators.required]),
      startDate : new FormControl("", [Validators.required]),
      endDate : new FormControl("", [Validators.required]),
      isProgrammed : new FormControl(false, [Validators.required])
    })
  
    activityVault(){
      // let data : ExpenseDto | RevenueDto = {
      //   amount?
      //   category?
      //   causal?
      //   endDate?:
      //   id?:
      //   isProgrammed
      //   startDate?
      //   thirdPartys?
      //   type?
      //   vault?:
      // };

      // this.vaultControllerService.createVault({body : data}).subscribe({
      //   next :(response) => {
      //     console.log("Vault Created !");
      //     this.dialogRef.close({delete : true})
      //   },
      //   error : (error) => {
      //     console.log("Error : ", error);
      //   }
      // })
    }
  
    // getUser() {
    //   this.userControllerService.getUserByJwt().subscribe({
    //     next: (response) => {
    //       this.principal = response;
    //     },
    //     error: (error) => {
    //       console.log('Error : ', error);
    //     },
    //   });
    // }
  }
  
