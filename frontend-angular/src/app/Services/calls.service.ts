import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';



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

  // Filter and search calls 
  filterAndSearchCalls(open?: string, fCat?: string, searchQuery?: string): Observable<any[]> {
    let params = new HttpParams();
    if (fCat) {
      params = params.set('fCat', fCat);
    }
    if (open) {
      params = params.set('open', open);
    }
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }
    return this.http.get<any[]>(`${this.apiUrl}/filterAndSearch`, { params });
  }

  getPaginatedCalls(
    pageNumber: number,
    pageSize: number,
    sortField: string = 'id',
    sortDirection: 'asc' | 'desc' = 'asc',
    searchQuery?: string,
    fCat?: string,
    open?: string
    // staffIds?: number[],
    // clientIds?: number[]
  ): Observable<any> {
    let params = new HttpParams();

    params = params.set('pageNumber', pageNumber.toString());
    params = params.set('pageSize', pageSize.toString());
    params = params.set('sortField', sortField);
    params = params.set('sortDirection', sortDirection);

    if (fCat) {
      params = params.set('fCat', fCat);
    }
    if (open) {
      params = params.set('open', open);
    }
    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }

    // // Append staffIds list to the params
    // if (staffIds && staffIds.length > 0) {
    //   staffIds.forEach((id) => {
    //     params = params.append('staffIds', id.toString());
    //   });
    // }

    // // Append customerIds list to the params
    // if (clientIds && clientIds.length > 0) {
    //   clientIds.forEach((id) => {
    //     params = params.append('customerIds', id.toString());
    //   });
    // }

    console.log('Params:', params);
    
    return this.http.get<any>(`${this.apiUrl}/advancedGet`, { params });
  }

}