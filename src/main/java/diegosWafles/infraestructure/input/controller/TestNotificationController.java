package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.IngredientService;
import diegosWafles.application.NotificationService;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.port.output.NotificationPort;
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
    public String testEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        try {
            notificationPort.sendEmail(to, subject, body);
            return "Email enviado exitosamente a: " + to;
        } catch (Exception e) {
            return "Error enviando email: " + e.getMessage();
        }
    }

    @PostMapping("/whatsapp")
    public String testWhatsApp(@RequestParam String phone, @RequestParam String message) {
        try {
            notificationPort.sendWhatsApp(phone, message);
            return "WhatsApp enviado exitosamente a: " + phone;
        } catch (Exception e) {
            return "Error enviando WhatsApp: " + e.getMessage();
        }
    }

    @PostMapping("/check-low-stock")
    public String checkLowStock() {
        List<Ingredient> allIngredients = ingredientService.listIngredients();
        notificationService.checkAndNotifyLowStock(allIngredients);
        return "Verificación de stock completada. Revisa los logs para más detalles.";
    }
}