import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { jwtDecode } from "jwt-decode";
import { Admin } from './admin.model';
// import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }



  //LOGIN and get token
  authenticateAndGetToken(username: string, password: string): Observable<any> {
    return this.http.post(`${this.BASE_URL}/authenticate`, { username, password }, { responseType: 'text' });
  }

  //LOGOUT and clear token
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }

  //GET TOKEN
  getToken(): string | null {
    return localStorage.getItem('token');
  }

//CHECK IF USER IS AUTHENTICATED
  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  //REGISTER
  register(data: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/register`, data);
  }

  //GET ADMIN USERNAME
  getAdminName(username: string): Observable<Admin> {
    return this.http.get<Admin>(`${this.BASE_URL}/findByUsername/${username}`);
  }

  //CHECK IF USERNAME IS TAKEN
  isUsernameTaken(username: string): Observable<boolean> {
    return this.http.get<{ exists: boolean }>(`${this.BASE_URL}/checkIfExist/${username}`)
      .pipe(
        map(response => response.exists)
      );
  }

  //CHECK IF USER IS ADMIN
  isAdmin(): boolean {
    return localStorage.getItem('role') === 'ROLE_ADMIN';
  }



  isTokenExpired(token: string): boolean {
    try {
      const decoded: any = jwtDecode(token);
      const expiry = decoded.exp * 1000; // exp is in seconds, convert to milliseconds
      return Date.now() > expiry;
    } catch (error) {
      return true;
    }
  }

}
