import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { CallsService } from 'src/app/Services/calls.service';
import { ClientsService } from 'src/app/Services/clients.service';
import { StaffService } from 'src/app/Services/staff.service';
import { PrintService } from 'src/app/Printing/print.service';

@Component({
  selector: 'app-add-call',
  templateUrl: './add-call.component.html',
  styleUrls: ['./add-call.component.css']
})
export class AddCallComponent implements OnInit {

  call: any = {};

  clientsList: any[] = [];
  staffList: any[] = [];

  selectedClient: any = null;
  selectedStaff: any = null;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private callsService: CallsService,
    private clientsService: ClientsService,
    private staffService: StaffService,
    private printService: PrintService
  ) { }

  ngOnInit(): void {
    this.loadClients();
    this.loadStaff();
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    console.log(this.call);

    this.callsService.addCall(this.call).subscribe({
      next: () => {
        this.successMessage = 'Call added successfully!';
        this.printCall();
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error adding call:', err);

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

  goBack(): void {
    this.router.navigate(['/calls/list']);
  }

  loadClients(): void {
    this.clientsService.getClients().subscribe(
      (data) => {
        this.clientsList = data;
      },
      (error) => {
        console.error('Error fetching clients data:', error);
      }
    );
  }

  loadStaff(): void {
    this.staffService.getStaff().subscribe(
      (data) => {
        this.staffList = data;
      },
      (error) => {
        console.error('Error fetching staff data:', error);
      }
    );
  }

  // Get full client object by ID when selected
  onClientSelect(clientId: number): void {
    this.clientsService.getClientById(clientId).subscribe(
      (client) => {
        this.selectedClient = client;
      },
      (error) => {
        console.error('Error fetching client:', error);
      }
    );
  }

  // Get full staff object by ID when selected
  onStaffSelect(staffId: number): void {
    this.staffService.getStaffById(staffId).subscribe(
      (staff) => {
        this.selectedStaff = staff;
      },
      (error) => {
        console.error('Error fetching staff:', error);
      }
    );
  }

  
  printCall() {
    this.printService.printDocument(
      `Call ${this.call.id}`,
      this.call,
      this.selectedStaff,
      this.selectedClient
    );
  }

  
}
