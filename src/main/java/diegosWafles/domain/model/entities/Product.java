package diegosWafles.domain.model.entities;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private Integer productID;
    private String productName;
    private BigDecimal productPrice;
    private List<ProductRecipe> productRecipes;

    public Product() {}

    public Product(Integer productID, String productName, BigDecimal productPrice, List<ProductRecipe> productRecipes) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productRecipes = productRecipes;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public List<ProductRecipe> getProductRecipes() {
        return productRecipes;
    }

    public void setProductRecipes(List<ProductRecipe> productRecipes) {
        this.productRecipes = productRecipes;
    }
}
