import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { OperationDto } from '../../api/models';
import { OperationControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';




@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrl: './activities.component.scss'
})
export class ActivitiesComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = 
  ['checkbox',
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
  dataSource = new MatTableDataSource<OperationDto>();  
  _liveAnnouncer: any;


  constructor(private operationControllerService : OperationControllerService,
    private authService: AuthService,
  ){}

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.getOperationsByPrincipal();
  }

  getOperationsByPrincipal() {
    this.operationControllerService.getAllOperationsByPrincipal().subscribe({
      next: (response) => {
        this.operations = response;
        this.dataSource.data = this.operations;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: () => {
        this.authService.logout();
      }
    })
  };

  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    // if (sortState.direction) {
    //   this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    // } else {
    //   this._liveAnnouncer.announce('Sorting cleared');
    // }
  }

}