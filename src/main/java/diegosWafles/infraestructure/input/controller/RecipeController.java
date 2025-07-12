package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.RecipeService;
import diegosWafles.domain.model.dto.RecipeDTO;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.model.entities.RecipeIngredient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public String createRecipe(@RequestBody RecipeDTO dto) {
        Recipe recipe = toDomain(dto);
        Recipe saved = service.saveRecipe(recipe);
        return "Recipe created with ID: " + saved.getRecipeID();
    }

    @GetMapping
    public List<RecipeDTO> listAllRecipes() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{recipeID}")
    public RecipeDTO getRecipe(@PathVariable Integer recipeID) {
        Recipe recipe = service.findByID(recipeID);
        return toDto(recipe);
    }

    @PutMapping("/{recipeID}")
    public String updateRecipe(@PathVariable Integer recipeID, @RequestBody RecipeDTO dto) {
        Recipe recipe = toDomain(dto);
        recipe.setRecipeID(recipeID);
        service.updateRecipe(recipe);
        return "Recipe updated successfully.";
    }

    @DeleteMapping("/{recipeID}")
    public String deleteRecipe(@PathVariable Integer recipeID) {
        service.deleteRecipe(recipeID);
        return "Recipe deleted successfully.";
    }

    // –– MAPPERS ––

    private RecipeDTO toDto(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setRecipeName(recipe.getRecipeName());
        dto.setIngredients(
                recipe.getRecipeIngredients().stream().map(ri -> {
                    RecipeDTO.IngredientDTO i = new RecipeDTO.IngredientDTO();
                    i.setIngredientID(ri.getIngredient().getIngredientID());
                    i.setRequiredQuantity(ri.getRequiredQuantity());
                    return i;
                }).collect(Collectors.toList())
        );
        return dto;
    }

    private Recipe toDomain(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(dto.getRecipeName());
        List<RecipeIngredient> ingredients = dto.getIngredients().stream()
                .map(i -> new RecipeIngredient(
                        null,
                        new Ingredient(i.getIngredientID(), null, null, null, null, null),
                        i.getRequiredQuantity()))
                .collect(Collectors.toList());
        recipe.setRecipeIngredients(ingredients);
        return recipe;
    }
}