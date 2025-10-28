import { Component } from '@angular/core';
import { UserDTO } from '../../../models/user.model';
import { AdminService } from '../../../services/admin.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent {

  users: UserDTO[] = [];

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.adminService.getEmployeeHistory().subscribe({
      next: users => this.users = users,
      error: err => console.error('Error al cargar empleados', err)
    });
  }

  deleteUser(user: UserDTO): void {
    if (confirm(`¿Seguro que quieres eliminar a ${user.name}?`)) {
      this.adminService.deleteEmployee(user.dpi).subscribe({
        next: () => this.loadEmployees(),
        error: err => console.error('Error al eliminar empleado', err)
      });
    }
  }

  editUser(user: UserDTO): void {
    console.log('Editar usuario', user);
  }
}