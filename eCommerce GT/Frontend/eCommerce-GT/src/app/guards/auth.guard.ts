import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role'];
    const user = this.authService.getCurrentUser();

    if (!user || !user.role) {
      Swal.fire({
        icon: 'warning',
        title: 'Acceso denegado',
        text: 'Debe iniciar sesión para acceder a esta página',
        confirmButtonText: 'Ir al login'
      }).then(() => this.router.navigate(['/login']));
      return false;
    }

    if (user.role?.trim() === expectedRole?.trim()) {
      return true;
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Acceso denegado',
        text: 'No tiene permisos para acceder a esta página',
        confirmButtonText: 'Ir al inicio'
      }).then(() => this.router.navigate(['/home']));
      return false;
    }
  }
}
