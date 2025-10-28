package backend.dto.management;

import backend.models.management.NotificationType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationAdminDTO {
    private Long idNotification;
    private String userDpi;
    private String userName;
    private String userEmail;
    private NotificationType type;
    private String subject;
    private String message;
    private boolean emailSent;
    private String errorMessage;
    private Date sendAt;
    private Date createdAt;
    private Date deliveryDate;
}