package backend.service;

import backend.dto.management.NotificationAdminDTO;
import backend.dto.management.NotificationUserDTO;
import backend.models.management.Notification;
import backend.models.management.NotificationType;
import backend.models.users.User;
import backend.repository.management.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Page<NotificationAdminDTO> getAdminReport(String userDpi, NotificationType type, Boolean sent, Date from, Date to, Pageable pageable) {
        Page<Notification> page = notificationRepository.findWithFilters(userDpi, type, sent, from, to, pageable);
        return page.map(this::toAdminDTO); // Usa método local
    }

    public Page<NotificationUserDTO> getUserNotifications(String userDpi, Pageable pageable) {
        User user = User.builder().dpi(userDpi).build();
        Page<Notification> page = notificationRepository.findByUserDpi(user, pageable);

        return page.map(this::toUserDTO);
    }

    private NotificationAdminDTO toAdminDTO(Notification notification) {
        if (notification == null) return null;

        User user = notification.getUser();
        return NotificationAdminDTO.builder()
                .idNotification(notification.getIdNotification())
                .userDpi(user.getDpi())
                .userName(user.getName())
                .userEmail(user.getEmail())
                .type(notification.getType())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .emailSent(notification.isEmailSent())
                .errorMessage(null)
                .sendAt(notification.getSendAt())
                .createdAt(notification.getCreatedAt())
                .deliveryDate(notification.getDeliveryDate())
                .build();
    }

    private NotificationUserDTO toUserDTO(Notification notification) {
        if (notification == null) return null;

        return NotificationUserDTO.builder()
                .idNotification(notification.getIdNotification())
                .type(notification.getType())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .emailSent(notification.isEmailSent())
                .sendAt(notification.getSendAt())
                .deliveryDate(notification.getDeliveryDate())
                .build();
    }
}
