import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import Swal from 'sweetalert2';
import { User } from '../../models/user.model';
import { RegisterService } from '../../services/register.service';

@Component({
  selector: 'register',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {
  dpi: string = ''
  userName: string = '';
  email: string = '';
  address: string = '';
  password: string = '';
  passwordConfirm: string = '';
  passwordVisible = false;
  passwordVisibleConfirm = false;

  constructor(private router: Router, private registerService: RegisterService) { }

  ngOnInit(): void {
    throw new Error("No implemntado");
  }

  formatDpi() {
    let clean = this.dpi.replace(/\D/g, '');
    clean = clean.substring(0, 13);

    if (clean.length > 10) {
      this.dpi = `${clean.substring(0, 4)} ${clean.substring(4, 9)} ${clean.substring(9)}`;
    } else if (clean.length > 4) {
      this.dpi = `${clean.substring(0, 4)} ${clean.substring(4)}`;
    } else {
      this.dpi = clean;
    }
  }

  validDpi(dpi: string): boolean {
    const cleanDpi = this.dpi.replace(/\s+/g, '');

    if (!/^\d{13}$/.test(cleanDpi)) {
      Swal.fire('Identificación inválida', 'El DPI que ha ingresado es incorrecto', 'error');
      return false;
    }
    return true;
  }

  register() {
    if (!this.validDpi(this.dpi)) return;
    if (!this.validEmail(this.email)) return;

    const campos = [
      { valor: this.dpi, mensaje: "Debe ingresar su número de DPI" },
      { valor: this.userName, mensaje: "Debe ingresar un nombre de usuario" },
      { valor: this.email, mensaje: "Debe ingresar un correo electrónico" },
      { valor: this.password, mensaje: "Debe ingresar una contraseña" },
      { valor: this.passwordConfirm, mensaje: "Debe confirmar su contraseña" }
    ];

    for (const campo of campos) {
      if (!campo.valor || campo.valor.trim() === '') {
        Swal.fire({
          icon: 'warning',
          title: 'Campos incompletos',
          text: campo.mensaje
        });
        return;
      }
    }

    if (this.password !== this.passwordConfirm) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Las contraseñas no coinciden'
      });
      return;
    }

    const newUser: User = {
      dpi: this.dpi.replace(/\s+/g, ''),
      name: this.userName,
      email: this.email,
      address: this.address,
      password: this.password
    };

    this.registerService.registerUser(newUser).subscribe({
      next: (user) => {
        this.registerService.showSuccess('Usuario registrado correctamente');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(err);
        this.registerService.showError('No se pudo registrar el usuario');
      }
    });
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  togglePasswordVisibilityConfirm() {
    this.passwordVisibleConfirm = !this.passwordVisibleConfirm;
  }

  validEmail(email: string): boolean {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!re.test(email)) {
      alert("El correo electrónico no es válido.");
      return false;
    }
    return true;
  }
}