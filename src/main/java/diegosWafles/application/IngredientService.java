package diegosWafles.application;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Provider;
import diegosWafles.domain.port.output.IngredientRepositoryPort;
import diegosWafles.domain.port.output.ProviderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepositoryPort ingredientRepo;
    private final ProviderRepositoryPort providerRepo;

    public IngredientService(IngredientRepositoryPort ingredientRepo, ProviderRepositoryPort providerRepo) {
        this.ingredientRepo = ingredientRepo;
        this.providerRepo = providerRepo;
    }

    public List<Ingredient> listIngredients() {
        return ingredientRepo.findAllIngredients();
    }

    public Ingredient searchByID(Integer ingredientID) {
        return ingredientRepo.findIngredientByID(ingredientID)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con ID: " + ingredientID));
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        if (ingredient.getIngredientName() == null || ingredient.getIngredientName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ingrediente es obligatorio");
        }

        if (ingredient.getIngredientQuantity() == null || ingredient.getIngredientQuantity().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cantidad del ingrediente debe ser mayor o igual a 0");
        }

        Provider provider = providerRepo.findProviderByID(ingredient.getProvider().getProviderID())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + ingredient.getProvider().getProviderID()));

        ingredient.setProvider(provider);
        return ingredientRepo.saveIngredientByID(ingredient);
    }

    public void deleteIngredient(Integer ingredientID) {
        Ingredient existing = ingredientRepo.findIngredientByID(ingredientID)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con ID: " + ingredientID));
        ingredientRepo.deleteIngredientByID(ingredientID);
    }
}