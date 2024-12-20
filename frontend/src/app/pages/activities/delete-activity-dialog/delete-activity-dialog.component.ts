import { Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ExpenseControllerService, RevenueControllerService } from '../../../api/services';

@Component({
  selector: 'app-delete-activity-dialog',
  templateUrl: './delete-activity-dialog.component.html',
  styleUrl: './delete-activity-dialog.component.scss'
})
export class DeleteActivityDialogComponent {
  data: any = inject<number>(MAT_DIALOG_DATA);

  constructor(private expenseControllerService : ExpenseControllerService,
    private revenueControllerService : RevenueControllerService,
    private dialogRef : MatDialogRef<DeleteActivityDialogComponent>
  ){}

  deleteActivity(){
    let id = this.data.operation.id;
    let type = this.data.operation.type;

    if(type == "REVENUE"){
      this.revenueControllerService.deleteRevenueById({id : id}).subscribe({
        next : (response) => {
          console.log("Revenue Deleted !");
          this.dialogRef.close();
        },
        error : (error) => {
          console.log("Error : ", error);
        }
      })
    }
    else if(type == "EXPENSE"){
      this.expenseControllerService.deleteExpenseById({id : id}).subscribe({
        next : (response) => {
          console.log("Expense Deleted !");
          this.dialogRef.close(); 
        },
        error : (error) => {
          console.log("Error : ", error);
        }
      })
    }
  
  }
}
