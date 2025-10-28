import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService, NotificationAdminDTO } from '../../../services/notification.service';

@Component({
  selector: 'app-notifications-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications-list.component.html',
  styleUrls: ['./notifications-list.component.css']
})
export class NotificationsListComponent implements OnInit {

  notifications: NotificationAdminDTO[] = [];
  page = 0;
  size = 10;
  totalElements = 0;
  totalPages = 0;

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.notificationService.getAllAdminNotifications().subscribe({
      next: (data) => {
        this.notifications = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = Math.ceil(this.totalElements / this.size);
      },
      error: (err) => console.error('Error al cargar notificaciones', err)
    });
  }

  get pagedNotifications(): NotificationAdminDTO[] {
    const start = this.page * this.size;
    return this.notifications.slice(start, start + this.size);
  }

  nextPage(): void {
    if (this.page + 1 < this.totalPages) {
      this.page++;
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
    }
  }
}
