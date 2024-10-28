import { Component, OnInit } from '@angular/core';
import { StaffService } from 'src/app/Services/staff.service';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-list-staff',
  templateUrl: './list-staff.component.html',
  styleUrls: ['./list-staff.component.css']
})
export class ListStaffComponent implements OnInit {

  successMessage: string | null = null;
  errorMessage: string | null = null;

  staffList: any[] = [];
  searchQuery: string = '';
  selectedDepartment: string | null = '';
  selectedStatus: string = '';

  currentPage: number = 0;
  pageSize: number = 10;

  totalPages: number = 1;
  totalRecords: number = 0;

  sortField: string = 'id';
  sortDirection: 'asc' | 'desc' = 'asc';

  departmentMap: { [key: string]: string } = {
    '': 'All Departments',
    'AD': 'Administration',
    'AC': 'Accounting',
    'HW': 'Hardware Maintenance',
    'SW': 'Software Maintenance'
  };

  departmentKeys: string[] = [''].concat(Object.keys(this.departmentMap).filter(key => key !== ''));

  private searchSubject = new Subject<string>();
  private pageSizeSubject = new Subject<number>();

  constructor(
    private router: Router,
    private staffService: StaffService,
  ) { }

  ngOnInit(): void {
    this.loadStaff();

    this.searchSubject.pipe(debounceTime(300))
      .subscribe((query) => {
        this.searchQuery = query;
        this.onSearch();
      });

    this.pageSizeSubject.pipe(
      debounceTime(500)
    ).subscribe(size => {
      this.changePageSize(size);
    });
  }

  loadStaff(): void {

    this.staffService.getPaginatedStaff(
      this.currentPage,
      this.pageSize,
      this.sortField,
      this.sortDirection,
      this.searchQuery,
      this.selectedDepartment
    ).subscribe(
      (data) => {
        console.log('Staff data:', data);
        this.staffList = data.content;
        this.totalPages = data.totalPages;
        this.totalRecords = data.totalElements;
        console.log('Total pages:', this.totalPages);
      },
      (error) => {
        console.error('Error fetching staff data:', error);
      }
    );
  }

  onSearchInputChange(query: string): void {
    this.searchSubject.next(query);
  }

  onPageSizeChange(value: number): void {
    this.pageSizeSubject.next(value);
  }

  // Navigate to add staff page
  addStaff() {
    this.router.navigate(['/staff/add']);
  }

  // Navigate to view staff page
  viewStaff(staff: any) {
    this.router.navigate([`/staff/view/${staff.id}`]);
  }

  // // Delete staff record
  // deleteStaff(staffId: number) {
  //   this.staffService.deleteStaff(staffId).subscribe(
  //     (data) => {
  //       console.log('Staff deleted successfully:', data);
  //       this.successMessage = 'Staff deleted successfully';
  //     },
  //     (error) => {
  //       console.error('Error deleting staff:', error);
  //       this.successMessage = 'Error deleting staff';
  //     }
  //   );
  //   this.staffList = this.staffList.filter(staff => staff.id !== staffId);
  // }

  // // Confirm delete staff record
  // confirmDelete(staffId: number) {
  //   const confirmation = confirm('Are you sure you want to delete this staff record?');
  //   if (confirmation) {
  //     this.deleteStaff(staffId);
  //   }
  // }

  // Search staff
  onSearch(): void {
    console.log('searchQuery:', this.searchQuery);
    this.currentPage = 0;
    this.loadStaff();
  }

  // Clear search query
  clearSearch(): void {
    this.searchQuery = '';
    this.loadStaff();
  }

  // Apply Department filter
  applyFilters(departmentCode: string): void {
    console.log('selectedDepartment:', this.selectedDepartment);
    this.selectedDepartment = departmentCode;
    this.currentPage = 0;
    this.loadStaff();
  }

  // Clear filters
  resetFilters(): void {
    this.searchQuery = '';
    this.selectedDepartment = '';
    this.currentPage = 0;
    this.pageSize = 10;
    this.sortDirection = 'asc';
    this.loadStaff();
  }

  // go to next page
  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      console.log('Next page:', this.currentPage);
      this.loadStaff();
    }
  }

  // go to previous page
  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      console.log('Previous page:', this.currentPage);
      this.loadStaff();
    }
  }

  firstPage(): void {
    this.currentPage = 0;
    this.loadStaff();
  }
  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadStaff();
  }

  // change sorting field
  changeSorting(field: string): void {
    this.sortField = field;
    this.currentPage = 0;
    this.loadStaff();
  }

  toggleSortDirection() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.currentPage = 0;
    this.loadStaff();
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.currentPage = 0;
    this.loadStaff();
  }

  ngOnDestroy(): void {
    this.pageSizeSubject.unsubscribe();
    this.searchSubject.unsubscribe();
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
}
