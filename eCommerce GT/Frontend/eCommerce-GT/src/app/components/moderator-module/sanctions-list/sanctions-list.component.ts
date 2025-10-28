import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { SanctionsService } from '../../../services/sanctions.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { SanctionDTO } from '../../../models/sanction.model';

@Component({
  selector: 'app-sanctions-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sanctions-list.component.html',
  styleUrl: './sanctions-list.component.css'
})
export class SanctionsListComponent implements OnInit {
  users: User[] = [];
  sanctions: SanctionDTO[] = [];
  violationTypes = [
    'Fraude',
    'Incumplimiento de entrega',
    'Producto prohibido'
  ];

  constructor(
    private userService: UserService,
    private sanctionsService: SanctionsService,
    private authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.loadUsers();
    this.loadSanctions();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudo obtener a los usuarios, intentelo más tarde', 'error');
        console.log('Error al cargar usuarios', err)
      }
    })
  }

  loadSanctions(): void {
    this.sanctionsService.getAllSanctions().subscribe({
      next: data => this.sanctions = data,
      error: err => {
        Swal.fire('Error', 'No se pudieron cargar las sanciones', 'error');
        console.error('Error al cargar sanciones', err);
      }
    });
  }

  reactivateSanction(sanction: SanctionDTO): void {
    Swal.fire({
      title: `¿Desea reactivar a ${sanction.userName}?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, reactivar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#198754',
      cancelButtonColor: '#6c757d'
    }).then(result => {
      if (result.isConfirmed) {
        this.sanctionsService.updateSanctionStatus(sanction.idSanction, false)
          .subscribe({
            next: updated => {
              sanction.status = updated.status;
              Swal.fire('Éxito', `El usuario ${sanction.userName} ha sido reactivado.`, 'success');
              this.loadSanctions();
            },
            error: err => {
              console.error('Error al reactivar usuario:', err);
              Swal.fire('Error', 'No se pudo reactivar al usuario.', 'error');
            }
          });
      }
    });
  }
}