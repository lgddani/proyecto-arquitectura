package diegosWafles.application;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.model.entities.RecipeIngredient;
import diegosWafles.domain.port.output.IngredientRepositoryPort;
import diegosWafles.domain.port.output.RecipeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepositoryPort recipeRepo;
    private final IngredientRepositoryPort ingredientRepo;

    public RecipeService(RecipeRepositoryPort recipeRepo, IngredientRepositoryPort ingredientRepo) {
        this.recipeRepo = recipeRepo;
        this.ingredientRepo = ingredientRepo;
    }

    public Recipe saveRecipe(Recipe recipe) {
        if (recipe.getRecipeName() == null || recipe.getRecipeName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la receta es obligatorio");
        }

        if (recipe.getRecipeIngredients() == null || recipe.getRecipeIngredients().isEmpty()) {
            throw new IllegalArgumentException("La receta debe tener al menos un ingrediente");
        }

        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            Ingredient ingredient = ingredientRepo.findIngredientByID(ri.getIngredient().getIngredientID())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con ID: " + ri.getIngredient().getIngredientID()));
            ri.setIngredient(ingredient);
        }

        return recipeRepo.saveRecipeWithIngredients(recipe);
    }

    public List<Recipe> findAll() {
        List<Recipe> recipes = recipeRepo.findAll();

        // Llenar la información completa de los ingredientes
        for (Recipe recipe : recipes) {
            for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
                Ingredient fullIngredient = ingredientRepo.findIngredientByID(ri.getIngredient().getIngredientID())
                        .orElse(null);
                if (fullIngredient != null) {
                    ri.setIngredient(fullIngredient);
                }
            }
        }

        return recipes;
    }

    public Recipe findByID(Integer recipeID) {
        Recipe recipe = recipeRepo.findByID(recipeID)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recipeID));

        // Llenar la información completa de los ingredientes
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            Ingredient fullIngredient = ingredientRepo.findIngredientByID(ri.getIngredient().getIngredientID())
                    .orElse(null);
            if (fullIngredient != null) {
                ri.setIngredient(fullIngredient);
            }
        }

        return recipe;
    }

    public void updateRecipe(Recipe recipe) {
        // Validaciones
        if (recipe.getRecipeName() == null || recipe.getRecipeName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la receta es obligatorio");
        }

        if (recipe.getRecipeIngredients() == null || recipe.getRecipeIngredients().isEmpty()) {
            throw new IllegalArgumentException("La receta debe tener al menos un ingrediente");
        }

        // Validar que la receta exista
        recipeRepo.findByID(recipe.getRecipeID())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recipe.getRecipeID()));

        // Validar que todos los ingredientes existan
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            Ingredient ingredient = ingredientRepo.findIngredientByID(ri.getIngredient().getIngredientID())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con ID: " + ri.getIngredient().getIngredientID()));
            ri.setIngredient(ingredient);
        }

        recipeRepo.updateRecipeWithIngredients(recipe);
    }

    public void deleteRecipe(Integer recipeID) {
        Recipe existing = recipeRepo.findByID(recipeID)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recipeID));
        recipeRepo.deleteByID(recipeID);
    }
}