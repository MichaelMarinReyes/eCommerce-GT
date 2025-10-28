package backend.service;

import backend.models.management.NotificationType;
import backend.models.users.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class SanctionEmailService {
    private final EmailService emailService;

    public SanctionEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    public void sendSanctionEmail(User user, String violationType, String reason, Date endDate) {
        String template = "sanction-notification";
        String subject = "Has recibido una sanción en eCommerce GT";

        Map<String, Object> model = Map.of(
                "userName", user.getName(),
                "violationType", violationType,
                "reason", reason,
                "endDate", endDate != null ? endDate.toString() : "No definido"
        );

        emailService.sendTemplatedEmail(user, NotificationType.SANCTION, subject, template, model, endDate);
    }
}