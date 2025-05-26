package diegosWafles.domain.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class RecipeDTO {
    private String recipeName;
    private List<IngredientDTO> ingredients;

    public static class IngredientDTO {
        private Integer ingredientID;
        private BigDecimal requiredQuantity;

        public Integer getIngredientID() {
            return ingredientID;
        }

        public void setIngredientID(Integer ingredientID) {
            this.ingredientID = ingredientID;
        }

        public BigDecimal getRequiredQuantity() {
            return requiredQuantity;
        }

        public void setRequiredQuantity(BigDecimal requiredQuantity) {
            this.requiredQuantity = requiredQuantity;
        }
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
