package diegosWafles.domain.port.output;

public interface NotificationPort {
    void sendEmail(String to, String subject, String body) throws Exception;
    void sendWhatsApp(String phoneNumber, String message) throws Exception;
}