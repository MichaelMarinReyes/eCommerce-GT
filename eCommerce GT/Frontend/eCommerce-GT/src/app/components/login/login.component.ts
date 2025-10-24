import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { AuthenticationService, LoginRequest, AuthResponse } from '../../services/authentication.service';

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
  ) { }

  login() {
    if (!this.email?.trim() || !this.password?.trim()) {
      alert("Debe llenar ambos campos para iniciar sesión");
      return;
    }

    const loginData: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (response: AuthResponse) => {
        console.log('Login exitoso:', response);
        // Guardar token si tu backend lo devuelve
        localStorage.setItem('token', response.token);
        this.router.navigate(['/common-user']);
      },
      error: (err) => {
        console.error('Error en login:', err);
        alert('Correo o contraseña incorrectos');
      }
    });
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

}
