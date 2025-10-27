import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderResponse } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) { }

  checkout(cartId: number, userDpi: string): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(`${this.apiUrl}/checkout/${cartId}/${userDpi}`, {});
  }

  getOrdersByUser(userDpi: string): Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(`${this.apiUrl}/user/${userDpi}`);
  }
}
