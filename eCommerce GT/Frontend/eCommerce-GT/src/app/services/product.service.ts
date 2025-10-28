import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.prod';
import { Rating } from '../models/rating.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {

    return this.http.get<Product[]>(`${this.apiUrl}/approved`);
  }

  createProduct(formData: FormData, userDpi: string): Observable<Product> {
    formData.append('userDpi', userDpi);
    return this.http.post<Product>(`${this.apiUrl}/create`, formData);
  }

  getProductByUser(userDpi: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/user/${userDpi}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/edit/${id}`);
  }

  updateProduct(id: number, formData: FormData, userDpi: string): Observable<Product> {
    // Agregamos userDpi dentro del FormData
    formData.append('userDpi', userDpi);
    return this.http.put<Product>(`${this.apiUrl}/update/${id}`, formData);
  }

  getProductExcludeById(userDpi: string) {
    return this.http.get<Product[]>(`${this.apiUrl}/exclude/${userDpi}`);
  }

  deleteProduct(id: number, userDpi: string): Observable<{ message: string, name?: string }> {
    return this.http.delete<{ message: string, name?: string }>(`${this.apiUrl}/delete/${id}`, {
      params: { userDpi }
    });
  }

  getProductWithRating(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/raiting/${id}`);
  }

  addRating(productId: number, userDpi: string, stars: number, comment: string): Observable<Rating> {
    return this.http.post<Rating>(`${this.apiUrl}/${productId}/rating`, { userDpi, stars, comment });
  }

  getPendingProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/pending`);
  }

  approveProduct(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/approve/${id}`, {});
  }

  rejectProduct(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/reject/${id}`, {});
  }

}