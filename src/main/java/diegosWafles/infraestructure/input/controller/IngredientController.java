package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.IngredientService;
import diegosWafles.domain.model.dto.IngredientDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> listIngredients() {
        try {
            List<Ingredient> ingredients = service.listIngredients();
            List<IngredientDTO> ingredientsDTO = ingredients.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Ingredientes consultados exitosamente",
                    true,
                    ingredientsDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar los ingredientes",
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public ResponseEntity<Object> createIngredient(@RequestBody IngredientDTO dto) {
        try {
            Ingredient ingredient = toDomain(dto);
            Ingredient saved = service.saveIngredient(ingredient);
            IngredientDTO savedDTO = toDto(saved);

            return ResponseHandler.generateResponse(
                    "Ingrediente creado exitosamente",
                    true,
                    savedDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al crear ingrediente",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al crear ingrediente",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{ingredientID}")
    public ResponseEntity<Object> getIngredient(@PathVariable Integer ingredientID) {
        try {
            Ingredient ingredient = service.searchByID(ingredientID);
            IngredientDTO ingredientDTO = toDto(ingredient);

            return ResponseHandler.generateResponse(
                    "Ingrediente consultado exitosamente",
                    true,
                    ingredientDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Ingrediente no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar el ingrediente",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{ingredientID}")
    public ResponseEntity<Object> updateIngredient(@PathVariable Integer ingredientID, @RequestBody IngredientDTO dto) {
        try {
            Ingredient ingredient = toDomain(dto);
            ingredient.setIngredientID(ingredientID);
            Ingredient updated = service.saveIngredient(ingredient);
            IngredientDTO updatedDTO = toDto(updated);

            return ResponseHandler.generateResponse(
                    "Ingrediente actualizado exitosamente",
                    true,
                    updatedDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al actualizar ingrediente",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al actualizar ingrediente",
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{ingredientID}")
    public ResponseEntity<Object> deleteIngredient(@PathVariable Integer ingredientID) {
        try {
            service.deleteIngredient(ingredientID);

            return ResponseHandler.generateResponse(
                    "Ingrediente eliminado exitosamente",
                    true
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Ingrediente no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al eliminar ingrediente",
                    e.getMessage()
            );
        }
    }

    // –– Mappers ––

    private IngredientDTO toDto(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getIngredientID(),
                ingredient.getIngredientName(),
                ingredient.getIngredientUnit().name(),
                ingredient.getIngredientQuantity(),
                ingredient.getMinimumQuantity(),
                ingredient.getProvider().getProviderID()
        );
    }

    private Ingredient toDomain(IngredientDTO dto) {
        Provider provider = new Provider();
        provider.setProviderID(dto.getProviderID());

        return new Ingredient(
                dto.getIngredientID(),
                dto.getIngredientName(),
                Ingredient.IngredientUnit.valueOf(dto.getIngredientUnit()),
                dto.getIngredientQuantity(),
                dto.getMinimumQuantity() != null ? dto.getMinimumQuantity() : BigDecimal.valueOf(10),
                provider
        );
    }
}