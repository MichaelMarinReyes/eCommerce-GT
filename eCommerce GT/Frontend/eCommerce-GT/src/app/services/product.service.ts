import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/active`);
  }

  createProduct(formData: FormData, userDpi: string): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/create/${userDpi}`, formData);
  }

  getProductByUser(userDpi: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/user/${userDpi}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/edit/${id}`);
  }

  updateProduct(id: number, formData: FormData, userDpi: string): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/update/${id}?userDpi=${userDpi}`, formData);
  }


  getProductExcludeById(userDpi: string) {
    return this.http.get<Product[]>(`${this.apiUrl}/exclude/${userDpi}`);
  }

  deleteProduct(id: number, userDpi: string): Observable<{ message: string, name?: string }> {
    const params = new HttpParams().set('userDpi', userDpi);
    return this.http.delete<{ message: string, name?: string }>(`${this.apiUrl}/delete/${id}`, { params });
  }
}