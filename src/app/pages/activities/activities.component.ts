import { Component, HostListener, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { firstValueFrom } from 'rxjs';
import { OperationDto, VaultDto, VaultSummary } from '../../api/models';
import { OperationControllerService, VaultControllerService } from '../../api/services';
import { CategoryDialogComponent } from '../../components/category-dialog/category-dialog.component';
import { ThirdPartyDialogComponent } from '../../components/third-party-dialog/third-party-dialog.component';
import { AuthService } from '../../security/auth.service';
import { ActivityDialogComponent } from './activity-dialog/activity-dialog.component';
import { DeleteActivityDialogComponent } from './delete-activity-dialog/delete-activity-dialog.component';
import { EditActivityDialogComponent } from './edit-activity-dialog/edit-activity-dialog.component';

type SortOrder = 'ASC' | 'DESC';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss'] // Modificato 'styleUrl' in 'styleUrls'
})
export class ActivitiesComponent implements OnInit {

  selected: VaultDto | null = null;
  dataReport: VaultSummary | null = null;
  isOpen = false;
  vaults?: Array<VaultDto>;


  displayedColumns: string[] = [
    'checkbox',
    'causal',
    'amount',
    'vaultName',
    'categoryName',
    'thirdPartyName',
    'startDate',
    'endDate',
    'status',
    'actions'
  ];
  
  operations?: Array<OperationDto>;
  private sortState: { [key: string]: SortOrder } = {};

  constructor(
    private operationControllerService: OperationControllerService,
    private vaultControllerService: VaultControllerService,
    private authService: AuthService,
    private dialog: MatDialog
  ) {}

  options: boolean = false;

  async ngOnInit() {
    
    await this.getVaultsByPrincipal();
    await this.getOperationsByVault();
    // this.getOperationsByPrincipal();
  }

  getOperationsByPrincipal() {
    this.operationControllerService.getAllOperationsByPrincipal().subscribe({
      next: (response) => {
         this.operations = response;
      },
      error: () => {
        this.authService.logout();
      }
    });
  }

  isRevenue(operation: OperationDto): boolean {
    return operation.type === 'REVENUE';
  }

  async refresh(){
    // this.getOperationsByPrincipal();
    await this.getOperationsByVault();
  }

  
  openOptions(){
    this.options = !this.options;

    console.log("Incoming Load Functions Soon!!!")
  }

  orderBy(header: string): void {
    if (!this.sortState[header]) {
      this.sortState[header] = 'ASC';
    } else {
      this.sortState[header] = this.sortState[header] === 'ASC' ? 'DESC' : 'ASC';
    }

    if (this.operations) {
      this.operations = this.applySort(header);
    }
  }

  private applySort(header: string): OperationDto[] {
    if (!this.sortState[header]) {
      return this.operations!;
    }

    return [...this.operations!].sort((a, b,) => {
      const fieldA = a[header as keyof OperationDto];
      const fieldB = b[header as keyof OperationDto];

      if (typeof fieldA === 'number' && typeof fieldB === 'number') {
        return this.sortState[header] === 'ASC' ? fieldA - fieldB : fieldB - fieldA;
      }
      
      if (typeof fieldA === 'string' && typeof fieldB === 'string') {
        return this.sortState[header] === 'ASC' ? fieldA.localeCompare(fieldB) : fieldB.localeCompare(fieldA);
      }
  
      if (header === 'startDate' || header === 'endDate') {
        const dateA = new Date(fieldA as string);
        const dateB = new Date(fieldB as string);
        return this.sortState[header] === 'ASC' ? dateA.getTime() - dateB.getTime() : dateB.getTime() - dateA.getTime();
      }

      return 0;
    });
  }

  openEditDialog(operation : OperationDto){
    this.dialog.open(EditActivityDialogComponent, {
      data : {operation : operation},
      width: "auto",
      height: "auto"
    }).afterClosed().subscribe({
     next : () => {
      // this.getOperationsByPrincipal();
      this.getOperationsByVault();
      }
    });
  }

  openDeleteDialog(operation : OperationDto){
    this.dialog.open(DeleteActivityDialogComponent, {
      data : {operation : operation},
      width: "auto",
      height: "auto"
    }).afterClosed().subscribe({
     next : () => {
      // this.getOperationsByPrincipal();
      this.getOperationsByVault();
      }
    });
  }

  openCreateDialog(){
    this.dialog.open(ActivityDialogComponent, {
      width : "auto",
      height : "auto"
    }).afterClosed().subscribe({
      next : () => {
        // this.getOperationsByPrincipal();
        this.getOperationsByVault();
      }
    })
  }


  openCategoryDialog(){
    this.dialog.open(CategoryDialogComponent, {
      width : "auto",
      height : "auto"
    }).afterClosed().subscribe({
      next : () => {
        // this.getOperationsByPrincipal();
        // this.getOperationsByVault();
      }
    })
  }

  openThirdPartyDialog(){
    this.dialog.open(ThirdPartyDialogComponent, {
      width : "auto",
      height : "auto"
    }).afterClosed().subscribe({
      next : () => {
        // this.getOperationsByPrincipal();
      }
    })
  }

  async getOperationsByVault() {
    try {
      this.operations = await firstValueFrom(this.operationControllerService.getAllOperationsByVaultId({ id : this.selected?.id!}));
    } catch (error) {
      console.log("Operazioni Non Disponibili");
      this.authService.logout();
    }
  }

  async getVaultsByPrincipal() {
    try {
      this.vaults = await firstValueFrom(this.vaultControllerService.getAllVaultsByPrincipal());
      if (this.vaults.length > 0) {
        this.selected = this.vaults[0];
      }
    } catch (error) {
      console.log("Dati Non Disponibili");
      this.authService.logout();
    }
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  async selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false;


    await this.getOperationsByVault();
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.custom-select')) {
      this.isOpen = false;
    }
  }

  chosen(chosenId: number): boolean {
    return chosenId == this.selected?.id;
  }
}
