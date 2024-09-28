import { Component, HostListener, OnInit } from '@angular/core';
import { OperationDto, VaultDto, VaultSummary } from '../../api/models';
import { OperationControllerService, VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit{

  constructor(private vaultControllerService : VaultControllerService,
    private operationControllerService : OperationControllerService,
    private authService: AuthService,
  ){}

  selected: VaultDto | null = null;
  dataReport : VaultSummary | null = null;
  isOpen = false;

  ngOnInit(): void {
    this.getVaultsByPrincipal();
    this.getOperationsByPrincipal();
    this.getSummaryReport();
  }

  vaults!: Array<VaultDto>;
  operations!: Array<OperationDto>;
  report!: Array<VaultSummary>;

  getVaultsByPrincipal() {
     this.vaultControllerService.getAllVaultsByPrincipal().subscribe({
      next: (response) => {
        this.vaults = response;

        if (this.vaults.length > 0) {
          this.selected = this.vaults[0];
        }
      },
      error: () => {
        this.authService.logout();
      }
    })
  };

  getOperationsByPrincipal() {
    this.operationControllerService.getAllOperationsByPrincipal().subscribe({
      next : (response) => {
        this.operations = response;
      },
      error : (error) => {
        this.authService.logout();
      }
    })
  }

  getSummaryReport(){
    this.vaultControllerService.getVaultsReport().subscribe({
      next : (response) =>{
        this.report = response;
        console.log(this.report);


      },
      error : (error) => {
        console.log("Error: Report not Available")
      }
    })
  }

  
  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false; 

    this.dataReport = this.report.find(a => a.vaultId === this.selected?.id)!;

    console.log(this.dataReport)
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.custom-select')) {
      this.isOpen = false;
    }
  }

  chosen(chosenId : number) : boolean{
    return chosenId == this.selected?.id;
  }

}