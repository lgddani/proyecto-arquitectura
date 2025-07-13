package diegosWafles.application;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.User;
import diegosWafles.domain.port.output.NotificationPort;
import diegosWafles.domain.port.output.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationPort notificationPort;
    private final UserRepositoryPort userRepository;
    private final Set<Integer> notifiedIngredients = new HashSet<>();

    public NotificationService(NotificationPort notificationPort, UserRepositoryPort userRepository) {
        this.notificationPort = notificationPort;
        this.userRepository = userRepository;
    }

    public void checkAndNotifyLowStock(List<Ingredient> ingredients) {
        // Paso 1: Revisar cuáles ingredientes ya no están en stock bajo para limpiar notificaciones
        List<Integer> recoveredIngredients = ingredients.stream()
                .filter(ing -> !ing.isBelowMinimum()) // Los que ya NO están en stock bajo
                .map(Ingredient::getIngredientID)
                .collect(Collectors.toList());

        // Limpiar notificaciones de ingredientes que se recuperaron
        recoveredIngredients.forEach(id -> {
            if (notifiedIngredients.remove(id)) {
                logger.info("✅ Ingrediente ID {} se recuperó del stock bajo. Listo para nuevas notificaciones.", id);
            }
        });

        // Paso 2: Encontrar ingredientes con stock bajo que aún no han sido notificados
        List<Ingredient> lowStockIngredients = ingredients.stream()
                .filter(Ingredient::isBelowMinimum)
                .filter(ing -> !notifiedIngredients.contains(ing.getIngredientID()))
                .collect(Collectors.toList());

        // Paso 3: Enviar notificaciones para nuevos casos de stock bajo
        if (!lowStockIngredients.isEmpty()) {
            List<User> admins = getAdministrators();
            logger.info("📧 Enviando notificaciones de stock bajo para {} ingrediente(s)", lowStockIngredients.size());

            for (Ingredient ingredient : lowStockIngredients) {
                notifyAdminsAboutLowStock(ingredient, admins);
                notifiedIngredients.add(ingredient.getIngredientID());
                logger.info("🔔 Ingrediente '{}' marcado como notificado", ingredient.getIngredientName());
            }
        } else {
            logger.debug("✅ No hay nuevos ingredientes con stock bajo para notificar");
        }
    }

    private void notifyAdminsAboutLowStock(Ingredient ingredient, List<User> admins) {
        String message = createLowStockMessage(ingredient);

        for (User admin : admins) {
            // Enviar email
            if (admin.getUserEmail() != null && !admin.getUserEmail().trim().isEmpty()) {
                try {
                    notificationPort.sendEmail(
                            admin.getUserEmail(),
                            "🧇 STOCK BAJO - " + ingredient.getIngredientName(),
                            message
                    );
                    logger.info("✅ Email enviado a {} sobre stock bajo de {}", admin.getUserEmail(), ingredient.getIngredientName());
                } catch (Exception e) {
                    logger.error("❌ Error enviando email a {}: {}", admin.getUserEmail(), e.getMessage());
                }
            }

            // Enviar WhatsApp
            if (admin.getUserPhone() != null && !admin.getUserPhone().trim().isEmpty()) {
                try {
                    notificationPort.sendWhatsApp(admin.getUserPhone(), message);
                    logger.info("✅ WhatsApp enviado a {} sobre stock bajo de {}", admin.getUserPhone(), ingredient.getIngredientName());
                } catch (Exception e) {
                    logger.error("❌ Error enviando WhatsApp a {}: {}", admin.getUserPhone(), e.getMessage());
                }
            }
        }
    }

    private String createLowStockMessage(Ingredient ingredient) {
        return String.format(
                "🧇 ALERTA DE STOCK BAJO - Diego's Wafles 🧇\n\n" +
                        "El ingrediente '%s' está próximo a agotarse.\n" +
                        "📊 Cantidad actual: %.2f %s\n" +
                        "⚠️ Cantidad mínima: %.2f %s\n" +
                        "🏪 Proveedor: %s\n\n" +
                        "Por favor, realizar pedido lo antes posible.",
                ingredient.getIngredientName(),
                ingredient.getIngredientQuantity(),
                ingredient.getIngredientUnit(),
                ingredient.getMinimumQuantity(),
                ingredient.getIngredientUnit(),
                ingredient.getProvider().getProviderName()
        );
    }

    private List<User> getAdministrators() {
        return userRepository.findAll().stream()
                .filter(user -> "Administrador".equals(user.getRole().getRolName()))
                .collect(Collectors.toList());
    }

    public void resetNotification(Integer ingredientID) {
        boolean removed = notifiedIngredients.remove(ingredientID);
        if (removed) {
            logger.info("🔄 Notificación resetada manualmente para ingrediente ID: {}", ingredientID);
        } else {
            logger.debug("ℹ️ Ingrediente ID {} no estaba en la lista de notificados", ingredientID);
        }
    }

    public Set<Integer> getNotifiedIngredients() {
        return new HashSet<>(notifiedIngredients);
    }

    public void clearAllNotifications() {
        int size = notifiedIngredients.size();
        notifiedIngredients.clear();
        logger.info("🗑️ Se limpiaron {} notificaciones pendientes", size);
    }
}