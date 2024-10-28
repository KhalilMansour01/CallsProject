import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PrintService } from '../print.service';

@Component({
  selector: 'app-print-layout',
  templateUrl: './print-layout.component.html',
  styleUrls: ['./print-layout.component.css']
})
export class PrintLayoutComponent implements OnInit {

  callDetails: any = {};
  staffDetails: any = {};
  clientDetails: any = {};

  constructor(
    private route: ActivatedRoute,
    private printService: PrintService
  ) { }

  ngOnInit() {
    // Check if the state contains call, staff, and client data
    if (history.state) {
      if (history.state.call) {
        this.callDetails = history.state.call;
      }
      if (history.state.staff) {
        this.staffDetails = history.state.staff;
      }
      if (history.state.client) {
        this.clientDetails = history.state.client;
      }
    }
  
    // Once the data is ready, trigger the print function
    this.printService.onDataReady(this.callDetails);
  }
  
}
