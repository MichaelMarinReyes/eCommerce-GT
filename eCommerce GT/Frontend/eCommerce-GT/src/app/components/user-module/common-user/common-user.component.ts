import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-common-user',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './common-user.component.html',
  styleUrl: './common-user.component.css'
})
export class CommonUserComponent {
  
  constructor(private authService: AuthenticationService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}