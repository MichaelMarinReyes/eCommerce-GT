import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

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

  constructor(private router: Router) { }
  ngOnInit(): void {
    
  }

  login() {
    if (!this.email?.trim() || !this.password?.trim()) {
      alert("Debe llenar ambos campos para iniciar sesión");
      return;
    }

    //alert(`Bienvenido, ${this.email}`);
    this.router.navigate(['/common-user']);
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

}
