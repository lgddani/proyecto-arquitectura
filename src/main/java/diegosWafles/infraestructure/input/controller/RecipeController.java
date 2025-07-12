package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.RecipeService;
import diegosWafles.domain.model.dto.RecipeDTO;
import diegosWafles.domain.model.dto.RecipeDetailDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.model.entities.RecipeIngredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createRecipe(@RequestBody RecipeDTO dto) {
        try {
            Recipe recipe = toDomain(dto);
            Recipe saved = service.saveRecipe(recipe);
            RecipeDetailDTO savedDTO = toDetailDto(saved);

            return ResponseHandler.generateResponse(
                    "Receta creada exitosamente",
                    true,
                    savedDTO
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al crear receta",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al crear receta",
                    e.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<Object> listAllRecipes() {
        try {
            List<Recipe> recipes = service.findAll();
            List<RecipeDetailDTO> recipesDTO = recipes.stream()
                    .map(this::toDetailDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Recetas consultadas exitosamente",
                    true,
                    recipesDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar las recetas",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{recipeID}")
    public ResponseEntity<Object> getRecipe(@PathVariable Integer recipeID) {
        try {
            Recipe recipe = service.findByID(recipeID);
            RecipeDetailDTO recipeDTO = toDetailDto(recipe);

            return ResponseHandler.generateResponse(
                    "Receta consultada exitosamente",
                    true,
                    recipeDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Receta no encontrada",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar la receta",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{recipeID}")
    public ResponseEntity<Object> updateRecipe(@PathVariable Integer recipeID, @RequestBody RecipeDTO dto) {
        try {
            Recipe recipe = toDomain(dto);
            recipe.setRecipeID(recipeID);
            service.updateRecipe(recipe);

            Recipe updated = service.findByID(recipeID);
            RecipeDetailDTO updatedDTO = toDetailDto(updated);

            return ResponseHandler.generateResponse(
                    "Receta actualizada exitosamente",
                    true,
                    updatedDTO
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al actualizar receta",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al actualizar receta",
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{recipeID}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Integer recipeID) {
        try {
            service.deleteRecipe(recipeID);

            return ResponseHandler.generateResponse(
                    "Receta eliminada exitosamente",
                    true
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Receta no encontrada",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al eliminar receta",
                    e.getMessage()
            );
        }
    }

    // –– MAPPERS ––

    private RecipeDetailDTO toDetailDto(Recipe recipe) {
        List<RecipeDetailDTO.RecipeIngredientDetailDTO> ingredients = recipe.getRecipeIngredients().stream()
                .map(ri -> new RecipeDetailDTO.RecipeIngredientDetailDTO(
                        ri.getIngredient().getIngredientID(),
                        ri.getIngredient().getIngredientName(),
                        ri.getIngredient().getIngredientUnit().name(),
                        ri.getRequiredQuantity()
                )).collect(Collectors.toList());

        return new RecipeDetailDTO(recipe.getRecipeID(), recipe.getRecipeName(), ingredients);
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