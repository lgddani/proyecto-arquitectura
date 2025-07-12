package diegosWafles.domain.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailDTO {
    private Integer productID;
    private String productName;
    private BigDecimal productPrice;
    private List<ProductRecipeDetailDTO> recipes;

    public static class ProductRecipeDetailDTO {
        private Integer recipeID;
        private String recipeName;

        public ProductRecipeDetailDTO() {}

        public ProductRecipeDetailDTO(Integer recipeID, String recipeName) {
            this.recipeID = recipeID;
            this.recipeName = recipeName;
        }

        // Getters y Setters
        public Integer getRecipeID() { return recipeID; }
        public void setRecipeID(Integer recipeID) { this.recipeID = recipeID; }

        public String getRecipeName() { return recipeName; }
        public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
    }

    public ProductDetailDTO() {}

    public ProductDetailDTO(Integer productID, String productName, BigDecimal productPrice, List<ProductRecipeDetailDTO> recipes) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.recipes = recipes;
    }

    // Getters y Setters
    public Integer getProductID() { return productID; }
    public void setProductID(Integer productID) { this.productID = productID; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public List<ProductRecipeDetailDTO> getRecipes() { return recipes; }
    public void setRecipes(List<ProductRecipeDetailDTO> recipes) { this.recipes = recipes; }
}