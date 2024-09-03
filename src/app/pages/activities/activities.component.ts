import { Component, OnInit } from '@angular/core';
import { OperationListsWrapper } from '../../api/models';
import { OperationControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrl: './activities.component.scss'
})
export class ActivitiesComponent implements OnInit {

  operations?: OperationListsWrapper;

  constructor(private operationControllerService : OperationControllerService,
    private authService: AuthService,
  ){
  }

  ngOnInit(): void {
    this.getOperationsByPrincipal();
  }

  getOperationsByPrincipal() {
    this.operationControllerService.getAllOperations().subscribe({
      next: (response) => {
        this.operations = response;
      },
      error: () => {
        this.authService.logout();
      }
    })
  };

}