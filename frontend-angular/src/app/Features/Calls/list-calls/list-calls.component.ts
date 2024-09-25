import { Component, OnInit } from '@angular/core';
import { CallsService } from 'src/app/Services/calls.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-calls',
  templateUrl: './list-calls.component.html',
  styleUrls: ['./list-calls.component.css']
})
export class ListCallsComponent implements OnInit {

  callsList: any[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  searchQuery: string = '';

  categoryMap: { [key: string]: string } = {
    '': 'All',
    'SOF': 'Software',
    'HDW': 'Hardware'
  };

  categoryKeys = Object.keys(this.categoryMap);
  selectedCategory: string = '';

  statusMap: { [key: string]: string } = {
    '': 'All',
    '1': 'Open',
    '0': 'Closed'
  };

  statusKeys = Object.keys(this.statusMap);
  selectedStatus: string = '';

  constructor(
    private callsService: CallsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCalls();
  }

  loadCalls(): void {
    if (this.searchQuery || this.selectedCategory || this.selectedStatus) {

      let statusFilter = this.selectedStatus ? this.selectedStatus : null;
      this.callsService.filterAndSearchCalls(
        statusFilter,
        this.selectedCategory,
        this.searchQuery
      ).subscribe(
        (data) => {
          this.callsList = data;
        },
        (error) => {
          console.error('Error fetching calls data:', error);
        }
      );
    } else {

      console.log('NO FILTERS!!');

      this.searchQuery = '';
      this.selectedCategory = '';
      this.selectedStatus = '';
      this.callsService.getCalls().subscribe(
        (data) => {
          this.callsList = data;
        },
        (error) => {
          console.error('Error fetching calls data:', error);
        }
      );
    }
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

  applyStatusFilter(status: string): void {
    console.log('Applying status filter:', status);
    this.selectedStatus = status;
    this.loadCalls();
  }

  applyCategoryFilter(category: string): void {
    console.log('Applying category filter:', category);
    this.selectedCategory = category;
    this.loadCalls();
  }

  onSearch(): void {
    console.log('Search query:', this.searchQuery);
    this.loadCalls();
  }

  clearFilters(): void {
    this.selectedCategory = '';
    this.selectedStatus = '';
    this.loadCalls();
  }
}
