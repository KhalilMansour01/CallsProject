import { Component, OnInit } from '@angular/core';
import { ClientsService } from 'src/app/Services/clients.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-clients',
  templateUrl: './list-clients.component.html',
  styleUrls: ['./list-clients.component.css']
})
export class ListClientsComponent implements OnInit {

  clientsList: any[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;
  
  searchQuery: string = '';

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

  countryKeys: string[] = Object.keys(this.countryMap);
  selectedCountry: string | null = '';

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

  regionKeys: string[] = Object.keys(this.regionMap);
  selectedRegion: string | null = '';

  constructor(
    private router: Router,
    private clientsService: ClientsService
  ) { }

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    if (this.searchQuery) {
      this.clientsService.searchClients(this.searchQuery).subscribe(
        (data) => {
          this.clientsList = data;
        },
        (error) => {
          console.error('Error fetching customer data:', error);
        }
      );
    } else if (this.selectedCountry || this.selectedRegion) {
      this.clientsService.filterByRegionAndCountry(this.selectedRegion, this.selectedCountry).subscribe(
        (data) => {
          this.clientsList = data;
        },
        (error) => {
          console.error('Error fetching customer data:', error);
        }
      );
    } else {
      this.clientsService.getClients().subscribe(
        (data) => {
          this.clientsList = data;
        },
        (error) => {
          console.error('Error fetching clients data:', error);
        }
      );
    }
  }

  addClient() {
    // Logic to add a new client
    console.log('Add Client button clicked');
    this.router.navigate(['/clients/add']);
  }

  editClient(client: any) {
    console.log('Edit button clicked for client ID:', client.id);
    if (client.id) {
      this.router.navigate([`/clients/edit/${client.id}`]);
    } else {
      console.error('Client ID is undefined');
    }
  }

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

  viewClient(client: any) {
    if (client.id) {
      this.router.navigate([`/clients/view/${client.id}`]);
    } else {
      console.error('Client ID is undefined');
    }
  }

  confirmDelete(clientId: number) {
    const confirmation = confirm('Are you sure you want to delete this client record?');
    if (confirmation) {
      this.deleteClient(clientId);
    }
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

  applyRegionFilter(newRegion: string): void {
    this.selectedRegion = newRegion;
    this.loadClients();
  }
  applyCountryFilter(newCountry: string): void {
    this.selectedCountry = newCountry;
    this.loadClients();
  }

  clearFilters(): void {
    this.selectedRegion = '';
    this.selectedCountry = '';
    this.loadClients();
  }

  onSearch(): void {
    if (this.searchQuery) {
      this.selectedRegion = '';
      this.selectedCountry = '';
      this.loadClients();
    } else {
      this.loadClients();
    }
  }
}
