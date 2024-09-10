import { Component, OnInit } from '@angular/core';
import { OperationDto } from '../../api/models';
import { OperationControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

type SortOrder = 'ASC' | 'DESC';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss'] // Modificato 'styleUrl' in 'styleUrls'
})
export class ActivitiesComponent implements OnInit {

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
    private authService: AuthService,
  ) {}

  options: boolean = false;

  ngOnInit(): void {
    this.getOperationsByPrincipal();
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

  refresh(){
    this.getOperationsByPrincipal();
  }

  
  openOptions(){
    this.options = !this.options;
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
}
