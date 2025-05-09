import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CallsService } from 'src/app/Services/calls.service';
import { ClientsService } from 'src/app/Services/clients.service';
import { StaffService } from 'src/app/Services/staff.service';
import { PrintService } from 'src/app/Printing/print.service';

@Component({
  selector: 'app-view-call',
  templateUrl: './view-call.component.html',
  styleUrls: ['./view-call.component.css']
})
export class ViewCallComponent implements OnInit {

  call: any = {};
  client: any = {};
  staff: any = {};

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private callsService: CallsService,
    private clientsService: ClientsService,
    private staffService: StaffService,
    private printService: PrintService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getCallById(id);
  }

  getCallById(id: number): void {
    this.callsService.getCallById(id).subscribe({
      next: (data) => {
        this.call = data;
        this.successMessage = 'Call details retrieved successfully';
        this.getClientById(data.ccode);
        this.getStaffById(data.ecode);
      },
      error: (error: any) => {
        this.errorMessage = error.message;
      }
    }
    );
  }

  getClientById(id: number): void {
    this.clientsService.getClientById(id).subscribe({
      next: (data) => {
        this.client = data;
      },
      error: (error: any) => {
        this.errorMessage = 'Failed to load staff details';
      }
    });
  }

  getStaffById(id: number): void {
    this.staffService.getStaffById(id).subscribe({
      next: (data) => {
        this.staff = data;
      },
      error: (error: any) => {
        this.errorMessage = 'Failed to load staff details';
      }
    });
  }

  followUpCall(): void {
    this.router.navigate([`/calls/followup/${this.call.id}`]);
  }

  goBack(): void {
    this.router.navigate(['/calls/list']);
  }

  // printCallDetails(): void {
  //   window.print();
  // }

  printCall() {
    this.printService.printDocument(
      `Call ${this.call.id}`,
      this.call,
      this.staff,
      this.client
    );
  }

  isCallOpen(): boolean {
    return this.call.open;
  }
}
