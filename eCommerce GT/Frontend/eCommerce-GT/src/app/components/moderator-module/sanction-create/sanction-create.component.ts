import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { SanctionDTO } from '../../../models/sanction.model';
import { SanctionsService } from '../../../services/sanctions.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { User } from '../../../models/user.model';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-sanction-create',
  imports: [CommonModule],
  templateUrl: './sanction-create.component.html',
  styleUrl: './sanction-create.component.css'
})
export class SanctionCreateComponent implements OnInit {
  users: User[] = [];
  violationTypes = [
    'Fraude',
    'Incumplimiento de entrega',
    'Producto prohibido'
  ];

  constructor(
    private sanctionsService: SanctionsService,
    private authService: AuthenticationService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: data => this.users = data,
      error: err => {
        Swal.fire('Error', 'No se pudieron cargar los usuarios', 'error');
        console.error('Error al cargar usuarios', err);
      }
    });
  }

  applySanction(user: User): void {
    Swal.fire({
      title: `Sancionar a ${user.name}`,
      html:
        `<label>Tipo de infracción:</label>
         <select id="violationType" class="swal2-input">
           ${this.violationTypes.map(v => `<option value="${v}">${v}</option>`).join('')}
         </select>
         <label>Motivo:</label>
         <textarea id="violationReason" class="swal2-textarea" placeholder="Describa el motivo"></textarea>`,
      showCancelButton: true,
      confirmButtonText: 'Sancionar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d',
      preConfirm: () => {
        const type = (document.getElementById('violationType') as HTMLSelectElement).value;
        const reason = (document.getElementById('violationReason') as HTMLTextAreaElement).value;
        if (!reason) {
          Swal.showValidationMessage('Debe ingresar un motivo');
          return false;
        }
        return { type, reason };
      }
    }).then(result => {
      if (result.isConfirmed) {
        const { type, reason } = result.value;
        const startDate = new Date().toISOString();
        const modDpi = this.authService.getCurrentUser().dpi || 'MODERATOR_DPI';

        this.sanctionsService.applySanction(user.dpi, modDpi, type, reason, startDate)
          .subscribe({
            next: (newSanction: SanctionDTO) => {
              Swal.fire('Éxito', `El usuario ${user.name} ha sido sancionado correctamente.`, 'success');
            },
            error: err => {
              console.error('Error al sancionar usuario:', err);
              Swal.fire('Error', 'No se pudo sancionar al usuario.', 'error');
            }
          });
      }
    });
  }
}