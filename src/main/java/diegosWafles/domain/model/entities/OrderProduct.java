package diegosWafles.domain.model.entities;

public class OrderProduct {
    private Integer orderID;
    private Product product;
    private Integer quantity;

    public OrderProduct() {}

    public OrderProduct(Integer orderID, Product product, Integer quantity) {
        this.orderID = orderID;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
