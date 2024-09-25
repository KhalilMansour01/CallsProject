import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  private apiUrl = 'http://localhost:8080/customer';

  constructor(private http: HttpClient) { }

  // Get all clients
  getClients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // Get client by id
  getClientById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  // Add client
  addClient(client: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, client);
  }

  // Update client
  updateClient(client: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${client.id}`, client);
  }

  // Delete client
  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  // Filter clients by region and country
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

  // Search clients by query
  searchClients(searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params });
  }

  // Filter and search clients
  filterAndSearchClients(region?: string, country?: string, searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (region) {
      params = params.set('regCode', region);
    }
    if (country) {
      params = params.set('cntrCode', country);
    }
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filterAndSearch`, { params });
  }
}