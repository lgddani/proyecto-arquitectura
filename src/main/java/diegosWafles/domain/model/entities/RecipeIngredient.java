package diegosWafles.domain.model.entities;

import java.math.BigDecimal;

public class RecipeIngredient {
    private Integer recipeID;
    private Ingredient ingredient;
    private BigDecimal requiredQuantity;

    public RecipeIngredient() {}

    public RecipeIngredient(Integer recipeID, Ingredient ingredient, BigDecimal requiredQuantity) {
        this.recipeID = recipeID;
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
    }

    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
