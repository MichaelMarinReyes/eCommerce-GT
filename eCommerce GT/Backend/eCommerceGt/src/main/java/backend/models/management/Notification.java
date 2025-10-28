package backend.models.management;

import backend.models.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification", nullable = false)
    private Long idNotification;
    @ManyToOne
    @JoinColumn(name = "user_dpi", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "message", nullable = false, length = 2000)
    private String message;
    @Column(name = "email_sent", nullable = false)
    private boolean emailSent;
    @Column(name = "send_at", nullable = false)
    private Date sendAt;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "delivery_date")
    private Date deliveryDate;
    @Column(name = "error_message")
    private String errorMessage;
}