import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryDto, ExpenseDto, RevenueDto, ThirdPartyDto, UserDto, VaultDto } from '../../../api/models';
import { CategoryControllerService, ExpenseControllerService, RevenueControllerService, ThirdPartyControllerService, UserControllerService, VaultControllerService } from '../../../api/services';

@Component({
  selector: 'app-activity-dialog',
  templateUrl: './activity-dialog.component.html',
  styleUrl: './activity-dialog.component.scss'
})
export class ActivityDialogComponent implements OnInit{

  principal! : UserDto;
  categories! : Array<CategoryDto> ;
  thirdParties! : Array<ThirdPartyDto> ;
  vaults! : Array<VaultDto>;
  operationTypes : Array<string> = ['REVENUE', 'EXPENSE'];

  vault: VaultDto | undefined;
  category: CategoryDto | undefined;
  thirdParty: ThirdPartyDto | undefined;

  constructor(private userControllerService : UserControllerService,
    private expenseControllerService : ExpenseControllerService,
    private revenueControllerService : RevenueControllerService,
    private vaultControllerService : VaultControllerService,
    private thirdPartyControllerService : ThirdPartyControllerService,
    private categoryControllerService : CategoryControllerService,
    private dialogRef : MatDialogRef<ActivityDialogComponent>
  ){}

  ngOnInit(): void {
    this.loadInformations();
  }

  activityForm = new FormGroup<any>({
    causal : new FormControl("", [Validators.minLength(3), Validators.required]),
    amount : new FormControl("", [Validators.min(0), Validators.required]),
    category : new FormControl(null,[Validators.required]),
    thirdParty : new FormControl(null,[Validators.required]),
    vault : new FormControl(null,[Validators.required]),
    type : new FormControl(null,[Validators.required]),
    startDate : new FormControl("", [Validators.required]),
    endDate : new FormControl("", [Validators.required]),
    isProgrammed : new FormControl(false, [Validators.required])
  })

  loadInformations(){
    this.getCategories();
    this.getThirdParties();
    this.getVaultsByPrincipal();
  }

  getCategories(){
    this.categoryControllerService.getAllCategories().subscribe({
      next : (response) => {
        this.categories = response;
      },
      error : (error) =>{
        console.log("Error : ", error);
      }
    })
  }

  getThirdParties(){
    this.thirdPartyControllerService.getAllThirdParties().subscribe({
      next : (response) => {
        this.thirdParties = response;
      },
      error : (error) =>{
        console.log("Error : ", error);
      }
    })
  }

  getVaultsByPrincipal() {
    this.vaultControllerService.getAllVaultsByPrincipal().subscribe({
      next: (response) => {
        this.vaults = response;
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    })
  };




  activityVault(){

    let values = this.activityForm.value;

    let data : ExpenseDto | RevenueDto = {
      amount : values.amount,
      categoryDTO : values.category,
      causal: values.causal,
      endDate: values.endDate,
      isProgrammed : values.isProgrammed,
      startDate : values.startDate,
      thirdPartyDTO : values.thirdParty,
      vaultDTO: values.vault ,
    };

    let type = values.type;
    console.log(data)
    if(values.type){

    }

    if(type == "REVENUE"){
      this.revenueControllerService.createRevenue({body : data}).subscribe({
        next : (response) => {
          console.log("Revenue Created !");
          this.dialogRef.close();
        },
        error : (error) => {
          console.log("Error : ", error);
        }
      })
    }
    else if(type == "EXPENSE"){
      this.expenseControllerService.createExpense({body : data}).subscribe({
        next : (response) => {
          console.log("Expense Created !");
          this.dialogRef.close(); 
        },
        error : (error) => {
          console.log("Error : ", error);
        }
      })
    }
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
  
