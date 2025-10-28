package backend.dto.management;

import backend.models.management.NotificationType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUserDTO {
    private Long idNotification;
    private NotificationType type;
    private String subject;
    private String message;
    private boolean emailSent;
    private Date sendAt;
    private Date deliveryDate;
}