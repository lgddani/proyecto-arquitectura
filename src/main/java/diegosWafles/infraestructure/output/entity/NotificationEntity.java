package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dw_notificacion")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Integer notificationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "ingrediente_id", nullable = false)
    private IngredientEntity ingredient;

    @Column(name = "tipo_notificacion", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime sentDate;

    @Column(name = "mensaje")
    private String message;

    @Column(name = "enviado_exitosamente")
    private boolean sentSuccessfully;

    // Getters
    public Integer getNotificationID() {
        return notificationID;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSentSuccessfully() {
        return sentSuccessfully;
    }

    // Setters
    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public void setIngredient(IngredientEntity ingredient) {
        this.ingredient = ingredient;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentSuccessfully(boolean sentSuccessfully) {
        this.sentSuccessfully = sentSuccessfully;
    }

    public enum NotificationType {
        EMAIL,
        WHATSAPP
    }
}