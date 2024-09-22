import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryDto } from '../../api/models';
import { CategoryControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-category-dialog',
  templateUrl: './category-dialog.component.html',
  styleUrl: './category-dialog.component.scss'
})
export class CategoryDialogComponent implements OnInit {

  items?: Array<CategoryDto>;

  constructor(private categoryControllerService: CategoryControllerService,
    private authService: AuthService,
    private dialogRef: MatDialogRef<CategoryDialogComponent>
  ) { }

  ngOnInit(): void {
    this.getItems();
  }

  itemForm = new FormGroup({
    name: new FormControl("", [Validators.minLength(3), Validators.required])
  })

  getItems() {
    this.categoryControllerService.getAllCategories().subscribe({
      next: (response) => {
         this.items = response;
      },
      error: () => {
        this.authService.logout();
      }
    });
  }

  createItem() {
    let data: CategoryDto = {
      id: 0,
      categoryName: this.itemForm.value.name!,
    }
    this.categoryControllerService.createCategory({ body: data }).subscribe({
      next: (response) => {
        console.log("Category Created !");
        this.dialogRef.close()
      },
      error: (error) => {
        console.log("Error : ", error);
      }
    })
  }
}
