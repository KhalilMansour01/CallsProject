import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StaffService } from 'src/app/Services/staff.service';

@Component({
  selector: 'app-view-staff',
  templateUrl: './view-staff.component.html',
  styleUrls: ['./view-staff.component.css']
})
export class ViewStaffComponent implements OnInit {

  staff: any = {};

  errorMessage: string | null = null;

  // Maps for codes to readable names
  civilCodeMap: { [key: string]: string } = {
    '1': 'Mr.',
    '2': 'Mrs.',
    '3': 'Dr.',
    '4': 'None'
  };

  departmentMap: { [key: string]: string } = {
    'AC': 'Accounting',
    'AD': 'Administration',
    'HW': 'Hardware Maintenance',
    'SW': 'Software Maintenance'
  };

  constructor(
    private route: ActivatedRoute,
    private staffService: StaffService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getStaffById(id);
  }

  getStaffById(id: number): void {
    this.staffService.getStaffById(id).subscribe({
      next: (data) => {
        this.staff = data;
      },
      error: (error: any) => {
        this.errorMessage = 'Error fetching staff details';
      }
    });
  }

  getCivilCode(code: string): string {
    return this.civilCodeMap[code] || 'Unknown';
  }

  getDepartmentName(code: string): string {
    return this.departmentMap[code] || 'Unknown';
  }

  goBack(): void {
    this.router.navigate(['/staff/list']);
  }
}
