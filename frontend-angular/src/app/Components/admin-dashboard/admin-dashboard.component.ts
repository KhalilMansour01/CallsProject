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
}