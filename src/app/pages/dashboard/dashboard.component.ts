import { Component, HostListener, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
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

  

  async ngOnInit(): Promise<void> {
    try {
      await this.getSummaryReport();  // Prima ottieni il report
      await this.getVaultsByPrincipal(); // Poi le vaults
      await this.getOperationsByPrincipal(); // Infine le operations
    } catch (error) {
      console.error('Error during initialization:', error);
    }
  }
  vaults?: Array<VaultDto>;
  operations?: Array<OperationDto>;
  report?: Array<VaultSummary> = [];

  async getVaultsByPrincipal() {
    try {
      this.vaults = await firstValueFrom(this.vaultControllerService.getAllVaultsByPrincipal());
  
      if (this.vaults.length > 0) {
        this.selected = this.vaults[0];
      }
  
      if (this.report && this.report.length > 0) {
        this.dataReport = this.report.find(a => a.vaultId == this.selected?.id)!;
      } else {
        console.error('Report is undefined or empty.');
      }
    } catch (error) {
      this.authService.logout();
    }
  }
  
  async getOperationsByPrincipal() {
    try {
      this.operations = await firstValueFrom(this.operationControllerService.getAllOperationsByPrincipal());
    } catch (error) {
      this.authService.logout();
    }
  }
  
  async getSummaryReport() {
    try {
      this.report = await firstValueFrom(this.vaultControllerService.getVaultsReport());
    } catch (error) {
      console.log("Error: Report not Available");
    }
  }
  

  
  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false; 

    if (this.report && this.report.length > 0) {
      this.dataReport = this.report.find(a => a.vaultId == this.selected?.id)!;
    } else {
      console.error('Report is undefined or empty.');
    }
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