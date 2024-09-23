import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CallsService {

  private apiUrl = 'http://localhost:8080/calls';

  constructor(private http: HttpClient) { }

  // Get all calls
  getCalls(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // Get call by id
  getCallById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  // Add call
  addCall(call: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, call);
  }

  // Update call
  updateCall(call: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${call.id}`, call);
  }

  // Delete call
  deleteCall(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  // Filter calls by open status and category
  filterCalls(open?: string, fCat?: string): Observable<any[]> {
    let params = new HttpParams();
    if (open) {
      params = params.set('open', open);
    }
    if (fCat) {
      params = params.set('fCat', fCat);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filter`, { params });
  }

  // Search calls by query
  searchCalls(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }
}