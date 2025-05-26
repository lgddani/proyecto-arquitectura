package diegosWafles.domain.model.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer orderID;
    private LocalDateTime createdAt;
    private String comment;
    private List<OrderProduct> orderProducts;

    public Order() {}

    public Order(Integer orderID, LocalDateTime createdAt, String comment, List<OrderProduct> orderProducts) {
        this.orderID = orderID;
        this.createdAt = createdAt;
        this.comment = comment;
        this.orderProducts = orderProducts;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
