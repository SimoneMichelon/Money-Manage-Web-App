import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoryDto, ThirdPartyDto, UserDto, VaultDto } from '../../api/models';
import { CategoryControllerService, ExpenseControllerService, RevenueControllerService, ThirdPartyControllerService, UserControllerService, VaultControllerService } from '../../api/services';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',

})
export class SettingsComponent {

  principal! : UserDto;
  categories! : Array<CategoryDto> ;
  thirdParties! : Array<ThirdPartyDto> ;
  vaults! : Array<VaultDto>;
  operationTypes : Array<string> = ['REVENUE', 'EXPENSE'];

  constructor(private userControllerService : UserControllerService,
    private expenseControllerService : ExpenseControllerService,
    private revenueControllerService : RevenueControllerService,
    private vaultControllerService : VaultControllerService,
    private thirdPartyControllerService : ThirdPartyControllerService,
    private categoryControllerService : CategoryControllerService
    // private dialogRef : MatDialogRef<ActivityDialogComponent>
  ){}

  ngOnInit(): void {
    this.loadInformations();
  }

  activityForm = new FormGroup({
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
    this.getUser();
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
        return response;
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    })
  };




  activityVault(){

    console.log(this.activityForm.value);

    // let data : ExpenseDto | RevenueDto = {
      // amount?
      // category?
      // causal?
      // endDate?:
      // id?:
      // isProgrammed
      // startDate?
      // thirdPartys?
      // type?
      // vault?:
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

