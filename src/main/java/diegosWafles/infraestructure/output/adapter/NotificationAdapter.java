package diegosWafles.infraestructure.output.adapter;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

    @Value("${twilio.whatsapp-from}")
    private String whatsappFrom;

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
    public void sendWhatsApp(String phoneNumber, String messageText) throws Exception {
        try {
            // Validar formato del número
            String formattedNumber = formatPhoneNumber(phoneNumber);

            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + formattedNumber),
                    new PhoneNumber(whatsappFrom),
                    messageText
            ).create();

            logger.info("✅ WhatsApp enviado exitosamente a: {} - SID: {}", formattedNumber, message.getSid());
        } catch (Exception e) {
            logger.error("❌ Error enviando WhatsApp a {}: {}", phoneNumber, e.getMessage());
            throw new Exception("Error al enviar WhatsApp: " + e.getMessage());
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Remover espacios y caracteres especiales
        String cleanNumber = phoneNumber.replaceAll("[^+\\d]", "");

        // Si no tiene código de país, agregar +593 (Ecuador)
        if (!cleanNumber.startsWith("+")) {
            if (cleanNumber.startsWith("0")) {
                cleanNumber = "+593" + cleanNumber.substring(1);
            } else if (cleanNumber.startsWith("593")) {
                cleanNumber = "+" + cleanNumber;
            } else {
                cleanNumber = "+593" + cleanNumber;
            }
        }

        return cleanNumber;
    }
}