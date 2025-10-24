import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AuthResponse {
  token: string;
  message: string;
  dpi: string;
  name: string;
  email: string;
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
  private baseUrl = 'http://localhost:8080/authentication';
  private currentUser: { token: string; name: string; email: string; dpi?: string } | null = null;

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

          this.currentUser = {
            token: res.token,
            name: res.name,
            email: res.email,
            dpi: res.dpi
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
      headers: { 'Authorization': `Bearer ${token}` }
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
      this.currentUser = { token, name, email, dpi };
    }
    return this.currentUser;
  }
}