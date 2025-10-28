export interface SanctionDTO {
  idSanction: number;
  userDpi: string;
  userName: string;
  moderatorDpi: string;
  moderatorName: string;
  reason: string;
  status: boolean;
  startDate: string;
  endDate?: string;
  violationType: string;
}