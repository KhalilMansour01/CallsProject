import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(): boolean {
    const token = this.authService.getToken();
    if (token && !this.authService.isTokenExpired(token)) {
      return true;
    } else if (token && this.authService.isTokenExpired(token)) {
      alert('Your session has expired. Please login again');
      this.authService.logout();
      this.router.navigate(['/login']);
      return false;
    }
    alert('You need to login first');
    this.router.navigate(['/login']);
    return false;
  }
}
