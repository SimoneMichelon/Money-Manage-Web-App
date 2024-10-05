import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CategoryDto, ExpenseDto, OperationDto, RevenueDto, ThirdPartyDto, UserDto, VaultDto } from '../../../api/models';
import { CategoryControllerService, ExpenseControllerService, RevenueControllerService, ThirdPartyControllerService, VaultControllerService } from '../../../api/services';
import { CategoryDialogComponent } from '../../../components/category-dialog/category-dialog.component';
import { ThirdPartyDialogComponent } from '../../../components/third-party-dialog/third-party-dialog.component';

@Component({
  selector: 'app-edit-activity-dialog',
  templateUrl: './edit-activity-dialog.component.html',
  styleUrl: './edit-activity-dialog.component.scss'
})
export class EditActivityDialogComponent implements OnInit {

  constructor(
    private expenseControllerService: ExpenseControllerService,
    private revenueControllerService: RevenueControllerService,
    private vaultControllerService: VaultControllerService,
    private thirdPartyControllerService: ThirdPartyControllerService,
    private categoryControllerService: CategoryControllerService,
    private dialogRef: MatDialogRef<EditActivityDialogComponent>,
    private dialog: MatDialog
  ) { }

  activityForm!: FormGroup;
  principal!: UserDto;
  operation!: OperationDto;
  categories!: Array<CategoryDto>;
  thirdParties!: Array<ThirdPartyDto>;
  vaults!: Array<VaultDto>;
  operationTypes: Array<string> = ['REVENUE', 'EXPENSE'];
  endDateCopy : boolean = false;
  data: any = inject<number>(MAT_DIALOG_DATA);

  ngOnInit(): void {
    this.loadInformations();
    this.loadActivity(this.data.operation);
  }

  loadActivity(operation: OperationDto) {
    this.activityForm = new FormGroup({
      causal: new FormControl(operation.causal, [Validators.minLength(3), Validators.required]),
      amount: new FormControl(operation.amount, [Validators.min(0), Validators.required]),
      category: new FormControl(null, [Validators.required]),
      thirdParty: new FormControl(null, [Validators.required]),
      vault: new FormControl(null, [Validators.required]),
      type: new FormControl(operation.type, [Validators.required]),
      startDate: new FormControl(operation.startDate, [Validators.required]),
      endDate: new FormControl(operation.endDate, [Validators.required]),
      isProgrammed: new FormControl(operation.isProgrammed, [Validators.required])
    });

    this.getCategories(operation.category);
    this.getThirdParties(operation.thirdParty);
    this.getVaults(operation.vault);
  }

  loadInformations() {
    this.getCategories();
    this.getThirdParties();
    this.getVaults();
  }



  getCategories(selectedCategory?: CategoryDto) {
    this.categoryControllerService.getAllCategories().subscribe({
      next: (response) => {
        this.categories = response;

        const defaultCategory = this.categories.find(cat => selectedCategory && cat.id === selectedCategory.id) || this.categories[0];
        this.activityForm.get('category')?.setValue(defaultCategory);
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    });
  }

  getThirdParties(selectedThirdParty?: ThirdPartyDto) {
    this.thirdPartyControllerService.getAllThirdParties().subscribe({
      next: (response) => {
        this.thirdParties = response;

        const defaultThirdParty = this.thirdParties.find(tp => selectedThirdParty && tp.id === selectedThirdParty.id) || this.thirdParties[0];
        this.activityForm.get('thirdParty')?.setValue(defaultThirdParty);
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    });
  }

  getVaults(selectedVault?: VaultDto) {
    this.vaultControllerService.getAllVaultsByPrincipal().subscribe({
      next: (response) => {
        this.vaults = response;

        const defaultVault = this.vaults.find(v => selectedVault && v.id === selectedVault.id) || this.vaults[0];
        this.activityForm.get('vault')?.setValue(defaultVault);
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    });
  }

  editActivity() {
    if (this.activityForm.valid) {
      let data: RevenueDto | ExpenseDto = {
        id: this.data.operation.id,
        causal: this.activityForm.value.causal,
        amount: this.activityForm.value.amount,
        categoryDTO: this.activityForm.value.category,
        thirdPartyDTO: this.activityForm.value.thirdParty,
        vaultDTO: this.activityForm.value.vault,
        endDate: this.activityForm.value.endDate,
        startDate: this.activityForm.value.startDate,
        isProgrammed: this.activityForm.value.isProgrammed

      };

      let type = this.activityForm.value.type;

      if (type == "REVENUE") {
        this.revenueControllerService.updateRevenue({ body: data }).subscribe({
          next: (response) => {
            console.log('Revenue Edited!');
            this.dialogRef.close();
          },
          error: (error) => {
            console.log('Error:', error);
          }
        })
      }
      else if (type == "EXPENSE") {
        this.expenseControllerService.updateExpense({ body: data }).subscribe({
          next: (response) => {
            console.log('Expense Edited!');
            this.dialogRef.close();
          },
          error: (error) => {
            console.log('Error:', error);
          }
        })
      }
    }
  }

  openCategoryDialog(){
    this.dialog.open(CategoryDialogComponent, {
      width : "auto",
      height : "auto",
    }).afterClosed().subscribe({
      next : () => {
        this.getCategories();
      }
    })
  }

  openThirdPartyDialog(){
    this.dialog.open(ThirdPartyDialogComponent, {
      width : "auto",
      height : "auto"
    }).afterClosed().subscribe({
      next : () => {
        this.getThirdParties();
      }
    })
  }

  copyStartDate(){
    this.endDateCopy = !this.endDateCopy;

    if(this.activityForm.value.startDate != this.activityForm.value.endDate || this.endDateCopy ){
      this.endDateCopy = true;

      this.activityForm.patchValue({
        endDate: this.activityForm.value.startDate
      });
    }
    else {
      this.activityForm.patchValue({
        endDate:''
      });
    }
    
  }

  dateValid(){
    return this.activityForm.value.startDate != '';
  }
}
