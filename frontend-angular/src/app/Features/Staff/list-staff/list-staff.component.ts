import { Component, OnInit } from '@angular/core';
import { StaffService } from 'src/app/Services/staff.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-staff',
  templateUrl: './list-staff.component.html',
  styleUrls: ['./list-staff.component.css']
})
export class ListStaffComponent implements OnInit {

  successMessage: string | null = null;
  errorMessage: string | null = null;

  staffList: any[] = [];
  selectedDepartment: string | null = '';
  searchQuery: string = '';


  departmentMap: { [key: string]: string } = {
    '': 'All Departments',
    'AD': 'Administration',
    'AC': 'Accounting',
    'HW': 'Hardware Maintenance',
    'SW': 'Software Maintenance'
  };

  // Create an array of keys for use in the template
  departmentKeys: string[] = Object.keys(this.departmentMap);

  constructor(
    private router: Router,
    private staffService: StaffService,
  ) { }

  ngOnInit(): void {
    this.loadStaff();
  }

  loadStaff(): void {

    if (this.searchQuery || this.selectedDepartment) {
      this.staffService.filterAndSearchStaff(this.selectedDepartment, this.searchQuery).subscribe(
        (data) => {
          this.staffList = data;
        },
        (error) => {
          console.error('Error fetching staff data:', error);
        }
      );
    } else {
      this.staffService.getStaff().subscribe(
        (data) => {
          this.staffList = data;
        },
        (error) => {
          console.error('Error fetching staff data:', error);
        }
      );
    }
  }

  addStaff() {
    this.router.navigate(['/staff/add']);
  }

  editStaff(staff: any) {
    if (staff.id) {
      this.router.navigate([`/staff/edit/${staff.id}`]);
    } else {
      console.error('Staff ID is undefined');
    }
  }

  viewStaff(staff: any) {
    if (staff.id) {
      this.router.navigate([`/staff/view/${staff.id}`]);
    } else {
      console.error('Staff ID is undefined');
    }
  }

  confirmDelete(staffId: number) {
    const confirmation = confirm('Are you sure you want to delete this staff record?');
    if (confirmation) {
      this.deleteStaff(staffId);
    }
  }

  deleteStaff(staffId: number) {
    this.staffService.deleteStaff(staffId).subscribe(
      (data) => {
        console.log('Staff deleted successfully:', data);
        this.successMessage = 'Staff deleted successfully';
      },
      (error) => {
        console.error('Error deleting staff:', error);
        this.successMessage = 'Error deleting staff';
      }
    );
    this.staffList = this.staffList.filter(staff => staff.id !== staffId);
  }

  getCivilName(cvlCode: number): string {
    switch (cvlCode) {
      case 1:
        return 'MR.';
      case 2:
        return 'MRS.';
      case 3:
        return 'DR.';
      default:
        return '';
    }
  }

  getDepartmentName(code: string | null): string {
    return this.departmentMap[code || ''] || 'Unknown';
  }

  applyFilters(departmentCode: string): void {
    console.log('selectedDepartment:', this.selectedDepartment);
    this.selectedDepartment = departmentCode === '' ? '' : departmentCode;
    this.loadStaff();
  }

  clearFilters(): void {
    this.selectedDepartment = '';
    this.loadStaff();
  }

  onSearch(): void {
    console.log('searchQuery:', this.searchQuery);
    this.loadStaff();
  }

}
