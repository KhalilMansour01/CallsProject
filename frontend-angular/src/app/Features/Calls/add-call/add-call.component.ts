import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { CallsService } from 'src/app/Services/calls.service';
import { ClientsService } from 'src/app/Services/clients.service';
import { StaffService } from 'src/app/Services/staff.service';

@Component({
  selector: 'app-add-call',
  templateUrl: './add-call.component.html',
  styleUrls: ['./add-call.component.css']
})
export class AddCallComponent implements OnInit {

  call: any = {};
  // call = {
  //   id: null,
  //   ccode: null,
  //   ecode: null,
  //   fcat: '',

  //   reqDate: '',
  //   reqTime: '',

  //   respDate: '',
  //   respTime: '',

  //   timeArrive: '',
  //   timeLeft: '',

  //   rem1: '',
  //   rem2: '',
  //   rem3: '',
  //   rem4: '',

  //   actRem1: '',
  //   actRem2: '',
  //   actRem3: '',
  //   actRem4: '',

  //   // fields not needed
  //   rpCode: null,
  //   suspFlag: '',
  //   invNo: null,
  // };

  clientsList: any[] = [];
  staffList: any[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private callsService: CallsService,
    private clientsService: ClientsService,
    private staffService: StaffService
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
        window.print();
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error adding call:', err);

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
  print(): void {
    window.print();
  }


preparePrintSection(): void {
  // Ensure the print section is visible and correctly populated
  const printSection = document.getElementById('print-section');
  if (printSection) {
    // Set up print content, if necessary
    // printSection.innerHTML = `
    //   <h1>Call Report</h1>
    //   <p>Call ID: ${this.call.id}</p>
    //   <p>Category: ${this.call.fcat}</p>
    //   <p>Client ID: ${this.call.ccode}</p>
    //   <p>Staff ID: ${this.call.ecode}</p>
    //   <p>Request Date: ${this.call.reqDate}</p>
    //   <p>Request Time: ${this.call.reqTime}</p>
    //   <p>Remark 1: ${this.call.rem1}</p>
    //   <p>Remark 2: ${this.call.rem2}</p>
    //   <p>Remark 3: ${this.call.rem3}</p>
    //   <p>Remark 4: ${this.call.rem4}</p>
    // `;
    // Show the print section
    printSection.style.display = 'block';
  }
  this.print();
}
}
