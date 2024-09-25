import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CallsService } from 'src/app/Services/calls.service';
import { ClientsService } from 'src/app/Services/clients.service';
import { StaffService } from 'src/app/Services/staff.service';


@Component({
  selector: 'app-followup-call',
  templateUrl: './followup-call.component.html',
  styleUrls: ['./followup-call.component.css']
})
export class FollowupCallComponent implements OnInit {

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

  updateCall(): void {
    this.callsService.updateCall(this.call).subscribe({
      next: () => {
        this.successMessage = 'Call updated successfully';
      },
      error: (err: any) => {
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
    this.router.navigate([`calls/view/${this.call.id}`]);
  }

}
