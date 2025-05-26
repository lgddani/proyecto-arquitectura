package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepositoryPort {
    Recipe saveRecipeWithIngredients(Recipe recipe);
    Optional<Recipe> findByID(Integer recipeID);
    List<Recipe> findAll();
    void updateRecipeWithIngredients(Recipe recipe);
    void deleteByID(Integer recipeID);
}
