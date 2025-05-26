package diegosWafles.infraestructure.output.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductRecipeID implements Serializable {

    private Integer productID;
    private Integer recipeID;

    public ProductRecipeID() {}

    public ProductRecipeID(Integer productID, Integer recipeID) {
        this.productID = productID;
        this.recipeID = recipeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRecipeID that)) return false;
        return Objects.equals(productID, that.productID) &&
                Objects.equals(recipeID, that.recipeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, recipeID);
    }
}
