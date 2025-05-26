package diegosWafles.infraestructure.output.entity;

import java.io.Serializable;
import java.util.Objects;

public class RecipeIngredientID implements Serializable {

    private Integer recipeID;
    private Integer ingredientID;

    public RecipeIngredientID() {}

    public RecipeIngredientID(Integer recipeID, Integer ingredientID) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeIngredientID that)) return false;
        return Objects.equals(recipeID, that.recipeID) &&
                Objects.equals(ingredientID, that.ingredientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeID, ingredientID);
    }
}
