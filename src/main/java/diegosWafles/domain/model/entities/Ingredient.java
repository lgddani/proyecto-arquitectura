package diegosWafles.domain.model.entities;

import java.math.BigDecimal;

public class Ingredient {
    private Integer ingredientID;
    private String ingredientName;
    private IngredientUnit ingredientUnit;
    private BigDecimal ingredientQuantity;
    private BigDecimal minimumQuantity;
    private Provider provider;

    public Ingredient() {}

    public Ingredient(Integer ingredientID, String ingredientName, IngredientUnit ingredientUnit,
                      BigDecimal ingredientQuantity, BigDecimal minimumQuantity, Provider provider) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.ingredientUnit = ingredientUnit;
        this.ingredientQuantity = ingredientQuantity;
        this.minimumQuantity = minimumQuantity;
        this.provider = provider;
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

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(IngredientUnit ingredientUnit) {
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean isBelowMinimum() {
        if (minimumQuantity == null || ingredientQuantity == null) {
            return false;
        }
        return ingredientQuantity.compareTo(minimumQuantity) <= 0;
    }

    public enum IngredientUnit {
        kg,
        gr,
        l,
        ml,
        u
    }
}