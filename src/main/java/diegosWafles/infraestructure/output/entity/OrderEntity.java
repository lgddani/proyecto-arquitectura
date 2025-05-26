package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dw_orden")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_id")
    private Integer orderID;

    @Column(name = "orden_comentario")
    private String comment;

    @Column(name = "orden_fecha")
    private LocalDateTime createdAt;

    // –– GETTERS ––
    public Integer getOrderID() {
        return orderID;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // –– SETTERS ––
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
