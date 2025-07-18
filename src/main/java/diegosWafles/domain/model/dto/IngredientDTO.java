package diegosWafles.domain.model.dto;

import java.math.BigDecimal;

public class IngredientDTO {
    private Integer ingredientID;
    private String ingredientName;
    private String ingredientUnit;
    private BigDecimal ingredientQuantity;
    private BigDecimal minimumQuantity;
    private Integer providerID;

    public IngredientDTO() {}

    public IngredientDTO(Integer ingredientID, String ingredientName, String ingredientUnit,
                         BigDecimal ingredientQuantity, BigDecimal minimumQuantity, Integer providerID) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.ingredientUnit = ingredientUnit;
        this.ingredientQuantity = ingredientQuantity;
        this.minimumQuantity = minimumQuantity;
        this.providerID = providerID;
    }

    public Integer getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(Integer ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public BigDecimal getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(BigDecimal ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public BigDecimal getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(BigDecimal minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Integer getProviderID() {
        return providerID;
    }

    public void setProviderID(Integer providerID) {
        this.providerID = providerID;
    }
}