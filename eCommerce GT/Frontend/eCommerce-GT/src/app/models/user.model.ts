export interface User {
  dpi: string;
  name: string;
  password?: string
  email?: string;
  address?: string;
  status?: boolean;
  role?: Role;
}

export interface Role {
  idRole: number;
  nameRole: string;
  description?: string;
}
