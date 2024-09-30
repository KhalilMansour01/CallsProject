import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClientsService } from 'src/app/Services/clients.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.css']
})
export class AddClientComponent implements OnInit {

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

  countryKeys: string[];
  regionKeys: string[];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private clientService: ClientsService
  ) { 
    this.countryKeys = Object.keys(this.countryMap);
    this.regionKeys = Object.keys(this.regionMap);
  }

  ngOnInit(): void {}

  onSubmit() {
    this.successMessage = null;
    this.errorMessage = null;

    this.clientService.addClient(this.client).subscribe({
      next: () => {
        this.successMessage = 'Client added successfully!';
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error adding client:', err);

        if (err.error && err.error.message) {
          this.errorMessage = err.error.message.split('\n');
        } else if (err.status === 0) {
          this.errorMessage = 'Could not connect to the server. Please try again later.';
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again.';
        }

      }
    });
  }

  goBack() {
    this.router.navigate(['/clients/list']);
  }

  closeErrorPopup() {
    this.errorMessage = null;
  }

  closeSuccessPopup() {
    this.successMessage = null;
  }

}
