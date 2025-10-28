import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SanctionDTO } from '../models/sanction.model';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class SanctionsService {
  private apiUrl = `${environment.apiUrl}/sanctions`;

  constructor(private http: HttpClient) { }

  getAllSanctions(): Observable<SanctionDTO[]> {
    return this.http.get<SanctionDTO[]>(`${this.apiUrl}/all-sanctions`);
  }

  applySanction(userDpi: string, moderatorDpi: string, violationType: string, reason: string, startDate?: string, endDate?: string): Observable<SanctionDTO> {
    const body = {
      userDpi,
      moderatorDpi,
      violationType,
      reason,
      startDate,
      endDate
    };

    return this.http.post<SanctionDTO>(`${this.apiUrl}/apply-sanction`, body);
  }

  updateSanctionStatus(sanctionId: number, status: boolean): Observable<SanctionDTO> {
    const params = new HttpParams().set('status', status.toString());
    return this.http.patch<SanctionDTO>(`${this.apiUrl}/${sanctionId}`, null, { params });
  }
}