package diegosWafles.application;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.model.entities.RecipeIngredient;
import diegosWafles.domain.port.output.RecipeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepositoryPort recipeRepo;

    public RecipeService(RecipeRepositoryPort recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepo.saveRecipeWithIngredients(recipe);
    }

    public List<Recipe> findAll() {
        return recipeRepo.findAll();
    }

    public Recipe findByID(Integer recipeID) {
        return recipeRepo.findByID(recipeID)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeID));
    }

    public void updateRecipe(Recipe recipe) {
        recipeRepo.updateRecipeWithIngredients(recipe);
    }

    public void deleteRecipe(Integer recipeID) {
        recipeRepo.deleteByID(recipeID);
    }
}
