import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import Swal from 'sweetalert2';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private apiUrl = `${environment.apiUrl}/authentication`;

  constructor(private http: HttpClient) { }

  registerUser(user: User): Observable<User> {
    user.status = true;
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  showSuccess(message: string) {
    Swal.fire({
      icon: 'success',
      title: '¡Éxito!',
      text: message
    });
  }

  showError(message: string) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: message
    });
  }
}
