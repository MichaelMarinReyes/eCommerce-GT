import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.prod';
import { CartModel, CartProduct } from '../models/cart.model';
import { ShoppingCart } from '../models/cart.model';

export interface CartResponseDTO {
  idCartProduct: number;
  cart: ShoppingCart;
  product: Product;
  quatity: number;
  price: number;
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

  getCartById(idCart: number, userDpi: string): Observable<CartModel> {
    return this.http.get<CartModel>(`${this.apiUrl}/get/${idCart}/${userDpi}`);
  }

  addToCart(userDpi: string, productId: number, quantity: number = 1): Observable<CartResponseDTO> {
    const params = new HttpParams()
      .set('userDpi', userDpi)
      .set('productId', productId)
      .set('quantity', quantity);
    return this.http.post<CartResponseDTO>(`${this.apiUrl}/add`, null, { params });
  }

  removeFromCart(userDpi: string, productId: number) {
    return this.http.delete(`${this.apiUrl}/remove`, {
      params: { userDpi, productId },
      responseType: 'text'
    });
  }

  clearCart(userDpi: string): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/clear/${userDpi}`);
  }

  checkout(userDpi: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/checkout/${userDpi}`, null);
  }

  updateQuantity(userDpi: string, productId: number, quantity: number): Observable<any> {
    const params = new HttpParams()
      .set('userDpi', userDpi)
      .set('productId', productId.toString())
      .set('quantity', quantity.toString());

    return this.http.put(`${this.apiUrl}/update-quantity`, null, { params });
  }
}