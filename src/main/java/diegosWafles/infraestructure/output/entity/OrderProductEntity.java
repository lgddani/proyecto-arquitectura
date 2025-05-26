package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dw_orden_producto")
@IdClass(OrderProductID.class)
public class OrderProductEntity {

    @Id
    @Column(name = "orden_id")
    private Integer orderID;

    @Id
    @Column(name = "producto_id")
    private Integer productID;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;

    // –– GETTERS ––
    public Integer getOrderID() {
        return orderID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // –– SETTERS ––
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
