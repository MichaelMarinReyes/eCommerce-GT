package backend.service;

import backend.models.management.Notification;
import backend.models.management.NotificationType;
import backend.models.users.User;
import backend.repository.management.NotificationRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Date;
import java.util.Map;

@Service
public class EmailService {
    private static final String FROM = "michaelmarin201831260@cunoc.edu.gt";
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final NotificationRepository notificationRepository;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine, NotificationRepository notificationRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.notificationRepository = notificationRepository;
    }

    @Async
    public void sendProductStatusEmail(User user, String productName, boolean approved) {
        String template = approved ? "product-approved" : "product-rejected";
        String subject = approved
                ? "¡Tu producto '" + productName + "' fue APROBADO!"
                : "Tu producto '" + productName + "' fue RECHAZADO";

        Map<String, Object> model = Map.of(
                "userName", user.getName(),
                "productName", productName,
                "status", approved ? "APROBADO" : "RECHAZADO"
        );
        sendTemplatedEmail(user, NotificationType.PRODUCT, subject, template, model, null);
    }

    @Async
    protected void sendTemplatedEmail(User user, NotificationType type, String subject, String templateName, Map<String, Object> model, Date deliveryDate) {
        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .subject(subject)
                .message("")
                .emailSent(false)
                .sendAt(new Date())
                .createdAt(new Date())
                .deliveryDate(deliveryDate)
                .build();
        notificationRepository.save(notification);

        try {
            Context context = new Context();
            context.setVariables(model);
            String html = templateEngine.process(templateName, context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(FROM);
            mailSender.send(message);

            notification.setMessage(html);
            notification.setEmailSent(true);
            notification.setSendAt(new Date());

        } catch (Exception e) {
            notification.setErrorMessage("ERROR: " + e.getMessage());
            notification.setEmailSent(false);
        }
        notificationRepository.save(notification);
    }
}