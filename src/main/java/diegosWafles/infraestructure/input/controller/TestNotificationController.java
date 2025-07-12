package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.IngredientService;
import diegosWafles.application.NotificationService;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.port.output.NotificationPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Object> simulateLowStock(@RequestParam String email) {
        try {
            String testMessage = """
                ⚠️ ALERTA DE STOCK BAJO ⚠️
                
                El ingrediente 'Harina' está próximo a agotarse.
                Cantidad actual: 5.00 kg
                Cantidad mínima: 10.00 kg
                Proveedor: Distribuidora ABC
                
                Por favor, realizar pedido lo antes posible.
                
                Este es un mensaje de prueba del sistema Diego's Wafles.
                """;

            notificationPort.sendEmail(email, "🧇 STOCK BAJO - Diego's Wafles", testMessage);

            return ResponseHandler.generateResponse(
                    "Email de prueba enviado exitosamente",
                    true,
                    "Email de stock bajo enviado a: " + email
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error enviando email de prueba",
                    e.getMessage()
            );
        }
    }
}