import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { StaffService } from 'src/app/Services/staff.service';

@Component({
  selector: 'app-add-staff',
  templateUrl: './add-staff.component.html',
  styleUrls: ['./add-staff.component.css']
})
export class AddStaffComponent implements OnInit {

  staff = {
    id: null,
    firstName: '',
    middleName: '',
    lastName: '',
    title: '',
    cvlCode: null,
    dptCode: null
  };

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private staffService: StaffService
  ) { }


  ngOnInit(): void {
  }

  onSubmit() {
    this.successMessage = null;
    this.errorMessage = null;
    this.staffService.addStaff(this.staff).subscribe({
      next: () => {
        this.successMessage = 'Staff added successfully!';
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error adding staff:', err);

        if (err.error && err.error.message) {
          this.errorMessage = err.error.message;
        } else if (err.status === 0) {
          this.errorMessage = 'Could not connect to the server. Please try again later.';
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again.';
        }

      }
    });
  }

  goBack() {
    this.router.navigate(['/staff/list']);
  }

}
