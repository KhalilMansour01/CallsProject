import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CallsService {

  private apiUrl = 'http://localhost:8080/calls';

  constructor(private http: HttpClient) { }

  getCalls(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  getCallById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addCall(call: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, call);
  }

  updateCall(call: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${call.id}`, call);
  }

  deleteCall(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

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

  searchCalls(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }
}
