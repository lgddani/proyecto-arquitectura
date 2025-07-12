package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.port.output.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NotificationAdapter implements NotificationPort {

    private static final Logger logger = LoggerFactory.getLogger(NotificationAdapter.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public NotificationAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            logger.info("✅ Email enviado exitosamente a: {}", to);
        } catch (Exception e) {
            logger.error("❌ Error enviando email a {}: {}", to, e.getMessage());
            throw new Exception("Error al enviar email: " + e.getMessage());
        }
    }

    @Override
    public void sendWhatsApp(String phoneNumber, String message) throws Exception {
        // Por ahora solo log, implementaremos después
        logger.info("📱 WhatsApp simulado enviado a: {} - Mensaje: {}", phoneNumber, message);
    }
}