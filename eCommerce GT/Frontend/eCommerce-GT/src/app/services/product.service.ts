import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/active`);
  }

  createProduct(product: Product, userDpi: string): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/create/${userDpi}`, product);
  }

  getProductByUser(userDpi: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/user/${userDpi}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/edit/${id}`); 
  }

  updateProduct(id: number, product: Product) {
    return this.http.post<Product[]>(`id`, product);
  }
}