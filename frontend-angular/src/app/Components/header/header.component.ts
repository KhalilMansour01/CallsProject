import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/Authentication/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated(); // Adjust according to your auth service method
  }

  logout(): void {
    this.authService.logout(); // Clear the authentication state
    this.router.navigate(['/login']); // Redirect to login page
  }
}
