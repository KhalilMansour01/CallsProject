import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  private apiUrl = 'http://localhost:8080/customer';

  constructor(private http: HttpClient) { }

  getClients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  getClientById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addClient(client: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, client);
  }

  updateClient(client: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${client.id}`, client);
  }

  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  filterByRegionAndCountry(region?: string, country?: string): Observable<any[]> {
    let params = new HttpParams();
    if (region) {
      params = params.set('regCode', region);
    }
    if (country) {
      params = params.set('cntrCode', country);
    }

    return this.http.get<any[]>(`${this.apiUrl}/filter`, { params });
  }

  searchClients(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }
}
