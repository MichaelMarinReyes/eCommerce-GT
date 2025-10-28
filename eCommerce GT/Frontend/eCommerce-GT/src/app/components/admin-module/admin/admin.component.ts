import { Component } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-view-admin',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  userName: string = ''

  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.userName = currentUser.name;
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
