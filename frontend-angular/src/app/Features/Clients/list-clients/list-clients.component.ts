import { Component, OnInit } from '@angular/core';
import { ClientsService } from 'src/app/Services/clients.service';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-list-clients',
  templateUrl: './list-clients.component.html',
  styleUrls: ['./list-clients.component.css']
})
export class ListClientsComponent implements OnInit {

  successMessage: string | null = null;
  errorMessage: string | null = null;

  clientsList: any[] = [];
  searchQuery: string = '';
  selectedCountry: string | null = '';
  selectedRegion: string | null = '';

  currentPage: number = 0;
  pageSize: number = 10;

  totalPages: number = 1;
  totalRecords: number = 0;

  sortField: string = 'id';
  sortDirection: 'asc' | 'desc' = 'asc';

  countryMap: { [key: string]: string } = {
    '': 'All Countries',
    '818': 'EGYPT',
    '276': 'GERMANY',
    '040': 'AUSTRIA',
    '380': 'ITALY',
    '826': 'UNITED KINGDOM',
    '756': 'SWITZERLAND',
    '100': 'BULGARIA',
    '392': 'JAPAN',
    '056': 'BELGIUM',
    '250': 'FRANCE',
    '764': 'THAILAND',
    '156': 'CHINA',
    '348': 'HUNGARY',
    '760': 'SYRIA',
    '414': 'KUWAIT',
    '400': 'JORDAN',
    '422': 'LEBANON'
  };

  regionMap: { [key: string]: string } = {
    '': 'All Regions',
    'BKA': 'BEKAA VALLEY',
    'BR': 'BEIRUT',
    'CTM': 'COAST OF METN',
    'ETB': 'EAST BEIRUT',
    'JHS': 'JOUNIEH & SUBURB',
    'JLS': 'JBEIL & SUBURB',
    'MTL': 'MOUNT LEBANON',
    'NTL': 'NORTH LEBANON',
    'SHL': 'SOUTH LEBANON',
    'STS': 'SOUTHERN SUBURB',
    'WTB': 'WEST BEIRUT'
  };

  countryKeys: string[] = [''].concat(Object.keys(this.countryMap).filter(key => key !== ''));
  regionKeys: string[] = [''].concat(Object.keys(this.regionMap).filter(key => key !== ''));

  private searchSubject = new Subject<string>();
  private pageSizeSubject = new Subject<number>();

  constructor(
    private router: Router,
    private clientsService: ClientsService
  ) { }

  ngOnInit(): void {
    this.loadClients();

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

  // Load clients data
  loadClients(): void {
    this.clientsService.getPaginatedClients(
      this.currentPage,
      this.pageSize,
      this.sortField,
      this.sortDirection,
      this.searchQuery,
      this.selectedRegion,
      this.selectedCountry
    ).subscribe(
      (data) => {
        console.log('Clients data:', data);
        this.clientsList = data.content;
        this.totalPages = data.totalPages;
        this.totalRecords = data.totalElements;
        console.log('Total pages:', this.totalPages);
      },
      (error) => {
        console.error('Error fetching clients data:', error);
      }
    );
  }

  onSearchInputChange(query: string): void {
    this.searchSubject.next(query);
  }

  onPageSizeChange(value: number): void {
    this.pageSizeSubject.next(value);
  }

  // Navigate to add client page
  addClient() {
    this.router.navigate(['/clients/add']);
  }

  // Navigate to view client page
  viewClient(client: any) {
    if (client.id) {
      this.router.navigate([`/clients/view/${client.id}`]);
    } else {
      console.error('Client ID is undefined');
    }
  }

  // Delete client record
  deleteClient(clientId: number) {
    this.clientsService.deleteClient(clientId).subscribe(
      (data) => {
        console.log('Client deleted successfully:', data);
      },
      (error) => {
        console.error('Error deleting client:', error);
      }
    );
    this.clientsList = this.clientsList.filter(client => client.id !== clientId);
  }

  // Confirm delete client record
  confirmDelete(clientId: number) {
    const confirmation = confirm('Are you sure you want to delete this client record?');
    if (confirmation) {
      this.deleteClient(clientId);
    }
  }

  // Search clients 
  onSearch(): void {
    console.log('searchQuery:', this.searchQuery);
    this.currentPage = 0;
    this.loadClients();
  }

  // Clear search query
  clearSearch(): void {
    this.searchQuery = '';
    this.loadClients();
  }

  // Apply Region filter
  applyRegionFilter(newRegion: string): void {
    console.log('selectedRegion:', this.selectedRegion);
    this.selectedRegion = newRegion;
    this.currentPage = 0;
    this.loadClients();
  }

  // Apply Country filter
  applyCountryFilter(newCountry: string): void {
    console.log('selectedCountry:', this.selectedCountry);
    this.selectedCountry = newCountry;
    this.currentPage = 0;
    this.loadClients();
  }

  // Clear filters
  resetFilters(): void {
    this.searchQuery = '';
    this.selectedRegion = '';
    this.selectedCountry = '';
    this.currentPage = 0;
    this.pageSize = 10;
    this.sortDirection = 'asc';
    this.loadClients();
  }

  // Go to next page
  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadClients();
    }
  }

  // Go to previous page
  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadClients();
    }
  }

  // Go to first page
  firstPage(): void {
    this.currentPage = 0;
    this.loadClients();
  }

  // Go to last page
  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadClients();
  }

  // Change sorting field
  changeSorting(field: string): void {
    this.sortField = field;
    this.currentPage = 0;
    this.loadClients();
  }

  // Toggle sorting direction
  toggleSortDirection() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.currentPage = 0;
    this.loadClients();
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.currentPage = 0;
    this.loadClients();
  }

  ngOnDestroy(): void {
    this.pageSizeSubject.unsubscribe();
    this.searchSubject.unsubscribe();
  }

  getCivilName(cvlCode: number): string {
    switch (cvlCode) {
      case 1:
        return 'Mr.';
      case 2:
        return 'Mrs.';
      case 3:
        return 'Dr.';
      default:
        return '';
    }
  }

  getCountryName(code: string | null): string {
    return this.countryMap[code || ''] || 'Unknown';
  }

  getRegionName(code: string | null): string {
    return this.regionMap[code || ''] || 'Unknown';
  }
}
