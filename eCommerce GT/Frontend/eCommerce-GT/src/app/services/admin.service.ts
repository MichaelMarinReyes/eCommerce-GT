import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, UserDTO } from '../models/user.model';
import { AuthenticationService } from './authentication.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = `${environment.apiUrl}/admin`;

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService) { }

  addEmployee(user: User): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/all -employees`, user);
  }

  updateEmployee(user: User): Observable<UserDTO> {
    return this.http.put<UserDTO>(`${this.apiUrl}/employees/${user.dpi}`, user);
  }

  getEmployeeHistory(): Observable<UserDTO[]> {
    const currentRole = this.authService.getCurrentUser()?.role || 'ADMINISTRADOR';

    return this.http.get<UserDTO[]>(`${this.apiUrl}/employees-history`).pipe(
      map((users: UserDTO[]) => users.filter((u: UserDTO) => u.roleName !== currentRole))
    );
  }

  deleteEmployee(dpi: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/employees/${dpi}`);
  }
}