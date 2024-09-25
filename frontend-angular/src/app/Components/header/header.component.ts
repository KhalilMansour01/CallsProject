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
    return this.authService.isAuthenticated();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // toAdminDashboard(): void {
  //   if(this.isAuthenticated()){
  //     this.router.navigate(['/admin-dashboard']);
  //   }
  // }
}
