package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "dw_ingrediente")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingrediente_id")
    private Integer ingredientID;

    @Column(name = "ingrediente_nombre", nullable = false)
    private String ingredientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ingrediente_unidad", nullable = false)
    private IngredientUnit ingredientUnit;

    @Column(name = "ingrediente_cantidad", precision = 10, scale = 2, nullable = false)
    private BigDecimal ingredientQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "proveedor_id", nullable = false)
    private ProviderEntity provider;

    // –– GETTERS ––
    public Integer getIngredientID() {
        return ingredientID;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public BigDecimal getIngredientQuantity() {
        return ingredientQuantity;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    // –– SETTERS ––
    public void setIngredientID(Integer ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientUnit(IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public void setIngredientQuantity(BigDecimal ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    // –– ENUM INTERNO ––
    public enum IngredientUnit {
        kg,
        gr,
        l,
        ml,
        u
    }
}
