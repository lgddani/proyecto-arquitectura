package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.IngredientService;
import diegosWafles.application.NotificationService;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.port.output.NotificationPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test")
public class TestNotificationController {

    private final NotificationPort notificationPort;
    private final NotificationService notificationService;
    private final IngredientService ingredientService;

    public TestNotificationController(NotificationPort notificationPort,
                                      NotificationService notificationService,
                                      IngredientService ingredientService) {
        this.notificationPort = notificationPort;
        this.notificationService = notificationService;
        this.ingredientService = ingredientService;
    }

    @PostMapping("/email")
    public ResponseEntity<Object> testEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String body) {
        try {
            notificationPort.sendEmail(to, subject, body);

            return ResponseHandler.generateResponse(
                    "Email enviado exitosamente",
                    true,
                    "Email enviado a: " + to
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error enviando email",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<Object> testWhatsApp(@RequestParam String phone,
                                               @RequestParam String message) {
        try {
            notificationPort.sendWhatsApp(phone, message);

            return ResponseHandler.generateResponse(
                    "WhatsApp enviado exitosamente",
                    true,
                    "WhatsApp enviado a: " + phone
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error enviando WhatsApp",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/check-low-stock")
    public ResponseEntity<Object> checkLowStock() {
        try {
            List<Ingredient> allIngredients = ingredientService.listIngredients();
            notificationService.checkAndNotifyLowStock(allIngredients);

            return ResponseHandler.generateResponse(
                    "Verificación de stock completada",
                    true,
                    "Revisa los logs para más detalles"
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error en verificación de stock",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/simulate-low-stock")
    public ResponseEntity<Object> simulateLowStock(@RequestParam String email,
                                                   @RequestParam(required = false) String phone) {
        try {
            String testMessage = """
                🧇 ALERTA DE STOCK BAJO - Diego's Wafles 🧇
                
                El ingrediente 'Harina' está próximo a agotarse.
                📊 Cantidad actual: 5.00 kg
                ⚠️ Cantidad mínima: 10.00 kg
                🏪 Proveedor: Distribuidora ABC
                
                Por favor, realizar pedido lo antes posible.
                """;

            // Enviar email
            notificationPort.sendEmail(email, "🧇 STOCK BAJO - Diego's Wafles", testMessage);

            String response = "Email de stock bajo enviado a: " + email;

            // Enviar WhatsApp si se proporciona número
            if (phone != null && !phone.trim().isEmpty()) {
                notificationPort.sendWhatsApp(phone, testMessage);
                response += " y WhatsApp enviado a: " + phone;
            }

            return ResponseHandler.generateResponse(
                    "Notificaciones de prueba enviadas exitosamente",
                    true,
                    response
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error enviando notificaciones de prueba",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/whatsapp-only")
    public ResponseEntity<Object> testWhatsAppOnly(@RequestParam String phone) {
        try {
            String testMessage = """
                🧇 ¡Hola desde Diego's Wafles! 🧇
                
                Este es un mensaje de prueba del sistema de notificaciones.
                
                ¡Gracias por usar nuestro servicio!
                """;

            notificationPort.sendWhatsApp(phone, testMessage);

            return ResponseHandler.generateResponse(
                    "Mensaje de WhatsApp de prueba enviado",
                    true,
                    "WhatsApp enviado a: " + phone
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error enviando WhatsApp de prueba",
                    e.getMessage()
            );
        }
    }

    // 🆕 NUEVOS ENDPOINTS PARA DEBUGGING

    @GetMapping("/notification-status")
    public ResponseEntity<Object> getNotificationStatus() {
        try {
            Set<Integer> notified = notificationService.getNotifiedIngredients();

            return ResponseHandler.generateResponse(
                    "Estado de notificaciones obtenido",
                    true,
                    "Ingredientes ya notificados: " + notified
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error obteniendo estado de notificaciones",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/reset-notification/{ingredientID}")
    public ResponseEntity<Object> resetNotification(@PathVariable Integer ingredientID) {
        try {
            notificationService.resetNotification(ingredientID);

            return ResponseHandler.generateResponse(
                    "Notificación resetada exitosamente",
                    true,
                    "Ingrediente ID " + ingredientID + " puede ser notificado nuevamente"
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error reseteando notificación",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/clear-all-notifications")
    public ResponseEntity<Object> clearAllNotifications() {
        try {
            notificationService.clearAllNotifications();

            return ResponseHandler.generateResponse(
                    "Todas las notificaciones limpiadas",
                    true,
                    "Todos los ingredientes pueden ser notificados nuevamente"
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error limpiando notificaciones",
                    e.getMessage()
            );
        }
    }
}