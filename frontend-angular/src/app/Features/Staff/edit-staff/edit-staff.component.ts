import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StaffService } from 'src/app/Services/staff.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-edit-staff',
  templateUrl: './edit-staff.component.html',
  styleUrls: ['./edit-staff.component.css']
})
export class EditStaffComponent implements OnInit {

  staff: any = {};

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private staffService: StaffService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id'); // Convert string to number
    this.getStaffById(id);
  }

  onSubmit() {
    this.successMessage = null;
    this.errorMessage = null;
    this.staffService.updateStaff(this.staff).subscribe({
      next: () => {
        this.successMessage = 'Staff updated successfully!';
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error updating staff:', err);

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

  getStaffById(id: number): void {
    this.staffService.getStaffById(id).subscribe((data) => {
      this.staff = {
        ...data,
        id: Number(data.id),
      };
    });
  }

  goBack() {
    this.router.navigate(['/staff/list']);
  }


}
