import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Authentication/auth.service';
import { Admin } from 'src/app/Authentication/admin.model';


@Component({
    selector: 'app-admin-dashboard',
    templateUrl: './admin-dashboard.component.html',
    styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
    adminName: string | null = null;
    errorMessage: string | null = null;

    constructor(private authService: AuthService, private router: Router) { }

    ngOnInit(): void {
        this.loadAdmin();
        // this.initChart();
    }

    loadAdmin(): void {
        const username = localStorage.getItem('username');
        if (username) {
            console.log('Retrieving admin info for:', username);
            this.authService.getAdminName(username).subscribe({
                next: (response) => {
                    this.adminName = response.firstName + ' ' + response.lastName;
                },
                error: (err) => {
                    console.error('Failed to retrieve admin info:', err);
                }
            });
        }
    }

    // initChart(): void {
    //     const ctx = document.getElementById('chart') as HTMLCanvasElement;

    //     new Chart(ctx, {
    //         type: 'bar', // You can change this to 'line', 'pie', etc.
    //         data: {
    //             labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    //             datasets: [{
    //                 label: 'Calls',
    //                 data: [12, 19, 3, 5, 2, 3, 7], // Dummy data
    //                 backgroundColor: 'rgba(75, 192, 192, 0.2)',
    //                 borderColor: 'rgba(75, 192, 192, 1)',
    //                 borderWidth: 1
    //             }]
    //         },
    //         options: {
    //             responsive: true,
    //             scales: {
    //                 y: {
    //                     beginAtZero: true
    //                 }
    //             }
    //         }
    //     });
    // }
}