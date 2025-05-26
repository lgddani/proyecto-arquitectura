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
                .orElseThrow(() -> new RuntimeException("No existe el ingrediente con ID: " + ingredientID));
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        Provider provider = providerRepo.findProviderByID(ingredient.getProvider().getProviderID())
                .orElseThrow(() -> new RuntimeException("No existe el proveedor: " + ingredient.getProvider().getProviderID()));

        ingredient.setProvider(provider);
        return ingredientRepo.saveIngredientByID(ingredient);
    }

    public void deleteIngredient(Integer ingredientID) {
        ingredientRepo.deleteIngredientByID(ingredientID);
    }
}
