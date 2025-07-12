package diegosWafles.infraestructure.output.adapter;

import com.twilio.Twilio;
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

    @Value("${twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth-token}")
    private String twilioAuthToken;

    @Value("${twilio.whatsapp-from}")
    private String twilioWhatsAppFrom;

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
            logger.info("Email enviado exitosamente a: {}", to);
        } catch (Exception e) {
            logger.error("Error enviando email: ", e);
            throw e;
        }
    }

    @Override
    public void sendWhatsApp(String phoneNumber, String message) throws Exception {
        try {
            // Inicializar Twilio
            Twilio.init(twilioAccountSid, twilioAuthToken);

            // Asegurar que el número tenga el formato correcto
            String formattedNumber = formatPhoneNumber(phoneNumber);

            Message twilioMessage = Message.creator(
                    new PhoneNumber("whatsapp:" + formattedNumber),
                    new PhoneNumber(twilioWhatsAppFrom),
                    message
            ).create();

            logger.info("WhatsApp enviado exitosamente a: {} - SID: {}", phoneNumber, twilioMessage.getSid());
        } catch (Exception e) {
            logger.error("Error enviando WhatsApp: ", e);
            throw e;
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Asegurar que el número tenga el código de país
        if (!phoneNumber.startsWith("+")) {
            // Asumiendo Ecuador (+593)
            return "+593" + phoneNumber.replaceAll("[^0-9]", "");
        }
        return phoneNumber;
    }
}