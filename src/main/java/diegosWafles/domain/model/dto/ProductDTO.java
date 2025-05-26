package diegosWafles.domain.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private Integer productID;
    private String productName;
    private BigDecimal productPrice;
    private List<Integer> recipeIDs;

    public ProductDTO() {}

    public ProductDTO(Integer productID, String productName, BigDecimal productPrice, List<Integer> recipeIDs) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.recipeIDs = recipeIDs;
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

    public List<Integer> getRecipeIDs() {
        return recipeIDs;
    }

    public void setRecipeIDs(List<Integer> recipeIDs) {
        this.recipeIDs = recipeIDs;
    }
}
