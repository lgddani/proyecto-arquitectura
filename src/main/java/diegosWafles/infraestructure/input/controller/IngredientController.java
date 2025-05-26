package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.IngredientService;
import diegosWafles.domain.model.dto.IngredientDTO;
import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Provider;
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
    public List<IngredientDTO> listIngredients() {
        return service.listIngredients().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public IngredientDTO createIngredient(@RequestBody IngredientDTO dto) {
        Ingredient ingredient = toDomain(dto);
        return toDto(service.saveIngredient(ingredient));
    }

    @GetMapping("/{ingredientID}")
    public IngredientDTO getIngredient(@PathVariable Integer ingredientID) {
        return toDto(service.searchByID(ingredientID));
    }

    @PutMapping("/{ingredientID}")
    public IngredientDTO updateIngredient(@PathVariable Integer ingredientID, @RequestBody IngredientDTO dto) {
        Ingredient ingredient = toDomain(dto);
        ingredient.setIngredientID(ingredientID);
        return toDto(service.saveIngredient(ingredient));
    }

    @DeleteMapping("/{ingredientID}")
    public void deleteIngredient(@PathVariable Integer ingredientID) {
        service.deleteIngredient(ingredientID);
    }

    private IngredientDTO toDto(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getIngredientID(),
                ingredient.getIngredientName(),
                ingredient.getIngredientUnit().name(),
                ingredient.getIngredientQuantity(),
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
                provider
        );
    }
}
