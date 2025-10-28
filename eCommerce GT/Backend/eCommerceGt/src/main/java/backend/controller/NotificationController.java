package backend.controller;

import backend.dto.management.NotificationAdminDTO;
import backend.dto.management.NotificationUserDTO;
import backend.models.management.NotificationType;
import backend.repository.management.NotificationRepository;
import backend.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final ReportService reportService;

    public NotificationController(NotificationRepository notificationRepository, ReportService reportService) {
        this.notificationRepository = notificationRepository;
        this.reportService = reportService;
    }

    @GetMapping("/admin-notifications")
    public ResponseEntity<Page<NotificationAdminDTO>> getAllAdminNotifications(Pageable pageable) {
        Page<NotificationAdminDTO> page = reportService.getAllAdminNotifications(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/my-notifications")
    public ResponseEntity<Page<NotificationUserDTO>> getMyNotifications(
            Principal principal,
            Pageable pageable) {

        Page<NotificationUserDTO> page = reportService.getUserNotifications(
                principal.getName(), pageable
        );

        return ResponseEntity.ok(page);
    }
}
