package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dw_producto")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer productID;

    @Column(name = "producto_nombre", nullable = false)
    private String productName;

    @Column(name = "producto_precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal productPrice;

    // –– GETTERS ––
    public Integer getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    // –– SETTERS ––
    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
