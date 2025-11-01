import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

export interface AuthResponse {
  token: string;
  message: string;
  dpi: string;
  name: string;
  email: string;
  role: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  dpi: string;
  name: string;
  email: string;
  password: string;
  address: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseUrl = `${environment.apiUrl}/authentication`;
  private currentUser: { token: string; name: string; email: string; dpi?: string, role?: string } | null = null;

  constructor(private http: HttpClient) { }

  login(request: LoginRequest): Observable<AuthResponse> {
    return new Observable(observer => {
      this.http.post<AuthResponse>(`${this.baseUrl}/login`, request).subscribe({
        next: (res) => {
          // Guardar datos en localStorage
          localStorage.setItem('token', res.token);
          localStorage.setItem('name', res.name);
          localStorage.setItem('email', res.email);
          if (res.dpi) localStorage.setItem('dpi', res.dpi);
          if (res.role) localStorage.setItem('role', res.role);

          this.currentUser = {
            token: res.token,
            name: res.name,
            email: res.email,
            dpi: res.dpi,
            role: res.role
          };

          observer.next(res);
          observer.complete();
        },
        error: (err) => observer.error(err)
      });
    });
  }

  logout(): void {
    const token = localStorage.getItem('token') || '';
    this.http.post(`${this.baseUrl}/logout`, {}, {
      headers: { 'Authorization': `Bearer ${token}` },
      responseType: 'text'
    }).subscribe();

    localStorage.clear();
    this.currentUser = null;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getCurrentUser() {
    if (!this.currentUser) {
      const token = localStorage.getItem('token') || '';
      const name = localStorage.getItem('name') || '';
      const email = localStorage.getItem('email') || '';
      const dpi = localStorage.getItem('dpi') || '';
      const role = localStorage.getItem('role') || '';
      this.currentUser = { token, name, email, dpi, role };
    }
    return this.currentUser;
  }
}