package diegosWafles.domain.model.entities;

public class ProductRecipe {
    private Integer productID;
    private Recipe recipe;

    public ProductRecipe() {}

    public ProductRecipe(Integer productID, Recipe recipe) {
        this.productID = productID;
        this.recipe = recipe;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
