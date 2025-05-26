package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "dw_receta_ingrediente")
@IdClass(RecipeIngredientID.class)
public class RecipeIngredientEntity {

    @Id
    @Column(name = "receta_id")
    private Integer recipeID;

    @Id
    @Column(name = "ingrediente_id")
    private Integer ingredientID;

    @Column(name = "cantidad_necesaria", precision = 10, scale = 2, nullable = false)
    private BigDecimal requiredQuantity;

    // –– GETTERS ––
    public Integer getRecipeID() {
        return recipeID;
    }

    public Integer getIngredientID() {
        return ingredientID;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    // –– SETTERS ––
    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public void setIngredientID(Integer ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
