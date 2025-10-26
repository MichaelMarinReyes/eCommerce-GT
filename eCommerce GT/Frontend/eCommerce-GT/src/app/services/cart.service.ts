import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.prod';

export interface CartProduct {
  product: Product;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = `${environment.apiUrl}/cart`;

  constructor(private http: HttpClient) { }

getCart(userDpi: string): Observable<CartProduct[]> {
    return this.http.get<CartProduct[]>(`${this.apiUrl}/${userDpi}`);
  }

  addToCart(userDpi: string, productId: number, quantity: number = 1): Observable<string> {
    const params = new HttpParams()
      .set('userDpi', userDpi)
      .set('productId', productId)
      .set('quantity', quantity);
    return this.http.post<string>(`${this.apiUrl}/add`, null, { params });
  }

  removeFromCart(userDpi: string, productId: number): Observable<string> {
    const params = new HttpParams()
      .set('userDpi', userDpi)
      .set('productId', productId);
    return this.http.delete<string>(`${this.apiUrl}/remove`, { params });
  }

  clearCart(userDpi: string): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/clear/${userDpi}`);
  }

  checkout(userDpi: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/checkout/${userDpi}`, null);
  }
}