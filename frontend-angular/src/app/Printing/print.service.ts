import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PrintService {

  callDetails: any = {};

  isPrinting = false;

  constructor(
    private router: Router
  ) { }

  printDocument(documentName: string, callData: any, staffData: any, clientData: any) {
    this.isPrinting = true;
    this.router.navigate([`/print`, documentName], {
      state: {
        call: callData,
        staff: staffData,
        client: clientData
      }
    });
  }
  

  onDataReady(callDetails: any) {
    setTimeout(() => {
      window.print();
      this.isPrinting = false;
      this.router.navigate([`/calls/view/${callDetails.id}`]);

      // this.router.navigate(['/calls/list']);
    });
  }

}
