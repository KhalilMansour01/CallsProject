import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class StaffService {

  private apiUrl = 'http://localhost:8080/staff';

  constructor(private http: HttpClient) { }

  getStaff(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  getStaffById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addStaff(staff: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, staff)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  updateStaff(staff: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${staff.id}`, staff);
  }

  deleteStaff(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

   // Call the department filter API
  filterByDepartment(dptCode: string): Observable<any[]> {
    let params = new HttpParams();
    if (dptCode) {
      params = params.set('dptCode', dptCode);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filter`, { params });
  }

  // Call the search API
  searchStaff(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }
  
}
