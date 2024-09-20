import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ClientsService } from 'src/app/Services/clients.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.css']
})
export class EditClientComponent implements OnInit {

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
    private clientsService: ClientsService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id'); // Convert string to number
    this.getClientById(id);

    this.countryKeys = Object.keys(this.countryMap);
    this.regionKeys = Object.keys(this.regionMap);
  }

  onSubmit() {
    this.successMessage = null;
    this.errorMessage = null;

    this.client.vatStatus = this.client.vatStatus ? '1' : '0';
    this.client.vatCash = this.client.vatCash ? '1' : '0';



    this.clientsService.updateClient(this.client).subscribe({
      next: () => {
        this.successMessage = 'Client updated successfully!';
        this.client.vatStatus = this.client.vatStatus === '1';
        this.client.vatCash = this.client.vatCash === '1';
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error updating client:', err);

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

  getClientById(id: number): void {
    this.clientsService.getClientById(id).subscribe((data) => {
      this.client = {
        ...data,
        id: Number(data.id),
        vatStatus: data.vatStatus === '1',  // Convert '1' to true, '0' to false
      vatCash: data.vatCash === '1',      // Convert '1' to true, '0' to false
      };
    });
  }

  goBack() {
    this.router.navigate(['/clients/list']);
  }

}
