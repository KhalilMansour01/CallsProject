import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ClientsService } from 'src/app/Services/clients.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-view-client',
  templateUrl: './view-client.component.html',
  styleUrls: ['./view-client.component.css']
})
export class ViewClientComponent implements OnInit {

  client: any = {};

  countryMap: { [key: string]: string } = {
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

  cvlCodeMap: { [key: string]: string } = {
    '1': 'Mr.',
    '2': 'Mrs.',
    '3': 'Dr.',
    '4': 'None',
  };

  countryKeys: string[];
  regionKeys: string[];
  cvlCodeKeys: string[];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private clientsService: ClientsService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id'); // Convert string to number
    this.getClientById(id);

    this.countryKeys = Object.keys(this.countryMap);
    this.regionKeys = Object.keys(this.regionMap);
    this.cvlCodeKeys = Object.keys(this.cvlCodeMap);
  }

  getClientById(id: number): void {
    this.clientsService.getClientById(id).subscribe({
      next: (data) => {
        this.client = {
          ...data,
          id: Number(data.id),
          cntrCodeName: this.countryMap[data.cntrCode] || 'Unknown',
          regCodeName: this.regionMap[data.regCode] || 'Unknown',
          vatStatus: data.vatStatus === '1',
          vatCash: data.vatCash === '1',
        };
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error fetching client data:', err);
        this.errorMessage = 'An error occurred while fetching the client details.';
      }
    });
  }

  goBack() {
    this.router.navigate(['/clients/list']);
  }

  editClient() {
    this.router.navigate([`/clients/edit/${this.client.id}`]);
  }

  printClientDetails() {
    window.print();
  }

  getCountryName(code: string): string {
    return this.countryMap[code] || 'Unknown';
  }

  getRegionName(code: string): string {
    return this.regionMap[code] || 'Unknown';
  }

  getCvlCodeName(code: string): string {
    return this.cvlCodeMap[code] || 'Unknown';
  }
}
