package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dw_producto_receta")
@IdClass(ProductRecipeID.class)
public class ProductRecipeEntity {

    @Id
    @Column(name = "producto_id")
    private Integer productID;

    @Id
    @Column(name = "receta_id")
    private Integer recipeID;

    // –– GETTERS ––
    public Integer getProductID() {
        return productID;
    }

    public Integer getRecipeID() {
        return recipeID;
    }

    // –– SETTERS ––
    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }
}
