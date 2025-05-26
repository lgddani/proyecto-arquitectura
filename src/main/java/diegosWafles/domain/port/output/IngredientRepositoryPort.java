package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientRepositoryPort {
    List<Ingredient> findAllIngredients();
    Optional<Ingredient> findIngredientByID(Integer ingredientID);
    Ingredient saveIngredientByID(Ingredient ingredient);
    void deleteIngredientByID(Integer ingredientID);
}
