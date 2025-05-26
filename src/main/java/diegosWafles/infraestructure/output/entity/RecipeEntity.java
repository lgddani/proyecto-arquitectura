package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dw_receta")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receta_id")
    private Integer recipeID;

    @Column(name = "receta_nombre", nullable = false)
    private String recipeName;

    // –– GETTERS ––
    public Integer getRecipeID() {
        return recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    // –– SETTERS ––
    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
