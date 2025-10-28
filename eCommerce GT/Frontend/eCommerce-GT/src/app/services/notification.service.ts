import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

export interface NotificationAdminDTO {
  idNotification: number;
  userDpi: string;
  type: string;
  subject: string;
  message: string;
  emailSent: boolean;
  sendAt: string;
  createdAt: string;
  deliveryDate?: string;
  errorMessage?: string;
}

export interface NotificationPage<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private apiUrl = `${environment.apiUrl}/notification`;

  constructor(private http: HttpClient) { }

  getAllAdminNotifications(): Observable<NotificationPage<NotificationAdminDTO>> {
    const params = new HttpParams()
      .set('page', '0')
      .set('size', '1000');

    return this.http.get<NotificationPage<NotificationAdminDTO>>(
      `${this.apiUrl}/admin-notifications`,
      { params }
    );
  }
}
