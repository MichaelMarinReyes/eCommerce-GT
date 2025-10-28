package backend.repository.management;

import backend.models.management.Notification;
import backend.models.management.NotificationType;
import backend.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserDpi(User user);

    Page<Notification> findByUserDpi(User user, Pageable pageable);

    List<Notification> findByType(NotificationType type);

    Page<Notification> findByType(NotificationType type, Pageable pageable);

    List<Notification> findByEmailSent(boolean emailSent);

    Page<Notification> findByEmailSent(boolean emailSent, Pageable pageable);

    List<Notification> findBySendAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Notification> findBySendAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.type = :type AND n.sendAt BETWEEN :start AND :end")
    Page<Notification> findByTypeAndSendAtBetween(
            @Param("type") NotificationType type,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable);

    @Query("""
            SELECT n FROM Notification n 
            WHERE (:userDpi IS NULL OR n.user.dpi = :userDpi)
              AND (:type IS NULL OR n.type = :type)
              AND (:emailSent IS NULL OR n.emailSent = :emailSent)
              AND (:startDate IS NULL OR n.sendAt >= :startDate)
              AND (:endDate IS NULL OR n.sendAt <= :endDate)
            ORDER BY n.sendAt DESC
            """)
    Page<Notification> findWithFilters(
            @Param("userDpi") String userDpi,
            @Param("type") NotificationType type,
            @Param("emailSent") Boolean emailSent,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    @Query("SELECT n FROM Notification n ORDER BY n.sendAt DESC")
    Page<Notification> findAllNotifications(Pageable pageable);

    @Query("SELECT n FROM Notification n ORDER BY n.sendAt DESC")
    List<Notification> findAllNotifications();
}
