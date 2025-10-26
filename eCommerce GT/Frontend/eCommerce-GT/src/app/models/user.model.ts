export interface User {
  dpi: string;
  name: string;
  email?: string;
  address?: string;
  status?: boolean;
  role?: Role;
}

export interface Role {
  idRole: number;
  roleName: string;
  description?: string;
}
