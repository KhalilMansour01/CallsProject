import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  private apiUrl = 'http://localhost:8080/staff';

  constructor(private http: HttpClient) { }

  // Get all staff
  getStaff(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // Get staff by id
  getStaffById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  // Add staff
  addStaff(staff: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, staff);
  }

  // Update staff
  updateStaff(staff: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${staff.id}`, staff);
  }

  // Delete staff
  deleteStaff(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  // Filter staff by department
  filterByDepartment(dptCode: string): Observable<any[]> {
    let params = new HttpParams();
    if (dptCode) {
      params = params.set('dptCode', dptCode);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filter`, { params });
  }

  // Search staff by query
  searchStaff(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }

  // Filter and search staff 
  filterAndSearchStaff(dptCode?: string, searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (dptCode) {
      params = params.set('dptCode', dptCode);
    }
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filterAndSearch`, { params });
  }
}