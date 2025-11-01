import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { AuthenticationService, LoginRequest, AuthResponse } from '../../services/authentication.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'login',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  userName: string = '';
  email: string = '';
  password: string = '';
  passwordVisible = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
    const currentuser = this.authService.getCurrentUser();
    
  }

  login() {
    if (!this.email?.trim() || !this.password?.trim()) {
      Swal.fire("Campos vacíos", "Debe llenar ambos campos para iniciar sesión", "info");
      return;
    }

    const loginData: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (response: AuthResponse) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);

        switch (response.role) {
          case 'ADMINISTRADOR':
            this.router.navigate(['/admin']);
            break;
          case 'MODERADOR':
            this.router.navigate(['/moderator']);
            break;
          case 'LOGÍSTICA':
            this.router.navigate(['/logistics']);
            break;
          case 'USUARIO COMÚN':
            this.router.navigate(['/common-user']);
            break;
          default:
            Swal.fire('Credenciales inválidas', 'Correo o contraseña incorrectos', 'error');
            this.email = '';
            this.password = '';
            break;
        }
      },
      error: (err) => {
        const msg = err.error?.message || 'Correo o contraseña incorrectos';
        Swal.fire('Acceso denegado', msg, 'error');
        this.email = '';
        this.password = '';
      }
    });
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

}
