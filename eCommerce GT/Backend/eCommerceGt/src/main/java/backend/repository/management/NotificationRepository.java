package backend.repository.management;

import backend.models.management.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class NotificationRepository {
    Optional<Notification> notification;
}
