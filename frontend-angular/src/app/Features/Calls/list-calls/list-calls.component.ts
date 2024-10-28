import { Component, OnInit } from '@angular/core';
import { CallsService } from 'src/app/Services/calls.service';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-list-calls',
  templateUrl: './list-calls.component.html',
  styleUrls: ['./list-calls.component.css']
})
export class ListCallsComponent implements OnInit {

  successMessage: string | null = null;
  errorMessage: string | null = null;

  callsList: any[] = [];
  searchQuery: string = '';
  selectedCategory: string = '';
  selectedStatus: string = '';

  staffIds: number[] = [];
  clientIds: number[] = [];

  currentPage: number = 0;
  pageSize: number = 10;

  totalPages: number = 1;
  totalRecords: number = 0;

  sortField: string = 'id';
  sortDirection: 'asc' | 'desc' = 'asc';

  categoryMap: { [key: string]: string } = {
    '': 'All Categories',
    'SOF': 'Software',
    'HDW': 'Hardware'
  };

  statusMap: { [key: string]: string } = {
    '': 'All Statuses',
    '1': 'Open',
    '0': 'Closed'
  };

  categoryKeys = [''].concat(Object.keys(this.categoryMap).filter(key => key !== ''));
  statusKeys = [''].concat(Object.keys(this.statusMap).filter(key => key !== ''));

  private searchSubject = new Subject<string>();
  private pageSizeSubject = new Subject<number>();

  constructor(
    private callsService: CallsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCalls();

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

  loadCalls(): void {
    this.callsService.getPaginatedCalls(
      this.currentPage,
      this.pageSize,
      this.sortField,
      this.sortDirection,
      this.searchQuery,
      this.selectedCategory,
      this.selectedStatus
    ).subscribe(
      (data) => {
        this.callsList = data.content;
        this.totalPages = data.totalPages;
        this.totalRecords = data.totalElements;
        console.log('Total elements', data.totalElements);
      },
      (error) => {
        console.error('Error fetching calls data:', error);
      }
    );
  }

  onSearchInputChange(query: string): void {
    this.searchSubject.next(query);
  }

  onPageSizeChange(value: number): void {
    this.pageSizeSubject.next(value);
  }

  addCall(): void {
    this.router.navigate(['/calls/add']);
  }

  viewCall(call: any): void {
    this.router.navigate([`/calls/view/${call.id}`]);
  }

  followupCall(call: any) {
    if (call.id) {
      this.router.navigate([`/calls/followup/${call.id}`]);
    } else {
      console.error('No call ID provided');
    }
  }

  confirmDelete(callsId: number): void {
    const confirmDelete = confirm('Are you sure you want to delete this call record?');
    if (confirmDelete) {
      this.deleteCall(callsId);
    }
  }

  deleteCall(callId: number): void {
    this.callsService.deleteCall(callId).subscribe(
      (data) => {
        console.log('Call deleted:', data);
      },
      (error) => {
        console.error('Error deleting call:', error);
      }
    );
    this.callsList = this.callsList.filter(call => call.id !== callId);
  }

  onSearch(): void {
    console.log('Search query:', this.searchQuery);
    this.currentPage = 0;
    this.loadCalls();
  }

  clearSearch(): void {
    this.searchQuery = '';
    this.loadCalls();
  }

  applyStatusFilter(status: string): void {
    console.log('Applying status filter:', status);
    this.selectedStatus = status;
    this.currentPage = 0;
    this.loadCalls();
  }

  applyCategoryFilter(category: string): void {
    console.log('Applying category filter:', category);
    this.selectedCategory = category;
    this.currentPage = 0;
    this.loadCalls();
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedCategory = '';
    this.selectedStatus = '';
    this.currentPage = 0;
    this.pageSize = 10;
    this.sortDirection = 'asc';
    this.loadCalls();
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadCalls();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadCalls();
    }
  }

  firstPage(): void {
    this.currentPage = 0;
    this.loadCalls();
  }

  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadCalls();
  }

  changeSortingField(field: string): void {
    this.sortField = field;
    this.currentPage = 0;
    this.loadCalls();
  }

  toggleSortDirection() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.currentPage = 0;
    this.loadCalls();
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.currentPage = 0;
    this.loadCalls();
  }

  ngOnDestroy(): void {
    this.pageSizeSubject.unsubscribe();
    this.searchSubject.unsubscribe();
  }

}
