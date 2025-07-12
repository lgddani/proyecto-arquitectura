package diegosWafles.domain.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class RecipeDetailDTO {
    private Integer recipeID;
    private String recipeName;
    private List<RecipeIngredientDetailDTO> ingredients;

    public static class RecipeIngredientDetailDTO {
        private Integer ingredientID;
        private String ingredientName;
        private String ingredientUnit;
        private BigDecimal requiredQuantity;

        public RecipeIngredientDetailDTO() {}

        public RecipeIngredientDetailDTO(Integer ingredientID, String ingredientName,
                                         String ingredientUnit, BigDecimal requiredQuantity) {
            this.ingredientID = ingredientID;
            this.ingredientName = ingredientName;
            this.ingredientUnit = ingredientUnit;
            this.requiredQuantity = requiredQuantity;
        }

        // Getters y Setters
        public Integer getIngredientID() { return ingredientID; }
        public void setIngredientID(Integer ingredientID) { this.ingredientID = ingredientID; }

        public String getIngredientName() { return ingredientName; }
        public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

        public String getIngredientUnit() { return ingredientUnit; }
        public void setIngredientUnit(String ingredientUnit) { this.ingredientUnit = ingredientUnit; }

        public BigDecimal getRequiredQuantity() { return requiredQuantity; }
        public void setRequiredQuantity(BigDecimal requiredQuantity) { this.requiredQuantity = requiredQuantity; }
    }

    public RecipeDetailDTO() {}

    public RecipeDetailDTO(Integer recipeID, String recipeName, List<RecipeIngredientDetailDTO> ingredients) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    // Getters y Setters
    public Integer getRecipeID() { return recipeID; }
    public void setRecipeID(Integer recipeID) { this.recipeID = recipeID; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public List<RecipeIngredientDetailDTO> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredientDetailDTO> ingredients) { this.ingredients = ingredients; }
}