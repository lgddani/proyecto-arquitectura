package diegosWafles.domain.model.entities;

import java.util.List;

public class Recipe {
    private Integer recipeID;
    private String recipeName;
    private List<RecipeIngredient> recipeIngredients;

    public Recipe() {}

    public Recipe(Integer recipeID, String recipeName, List<RecipeIngredient> recipeIngredients) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
    }

    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
