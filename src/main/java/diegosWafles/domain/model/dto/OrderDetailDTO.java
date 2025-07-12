package diegosWafles.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailDTO {
    private Integer orderID;
    private LocalDateTime createdAt;
    private String comment;
    private List<OrderProductDetailDTO> products;

    public static class OrderProductDetailDTO {
        private Integer productID;
        private String productName;
        private BigDecimal productPrice;
        private Integer quantity;
        private BigDecimal subtotal; // precio * cantidad

        public OrderProductDetailDTO() {}

        public OrderProductDetailDTO(Integer productID, String productName, BigDecimal productPrice,
                                     Integer quantity, BigDecimal subtotal) {
            this.productID = productID;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
            this.subtotal = subtotal;
        }

        // Getters y Setters
        public Integer getProductID() { return productID; }
        public void setProductID(Integer productID) { this.productID = productID; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public BigDecimal getProductPrice() { return productPrice; }
        public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    }

    public OrderDetailDTO() {}

    public OrderDetailDTO(Integer orderID, LocalDateTime createdAt, String comment, List<OrderProductDetailDTO> products) {
        this.orderID = orderID;
        this.createdAt = createdAt;
        this.comment = comment;
        this.products = products;
    }

    // Getters y Setters
    public Integer getOrderID() { return orderID; }
    public void setOrderID(Integer orderID) { this.orderID = orderID; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public List<OrderProductDetailDTO> getProducts() { return products; }
    public void setProducts(List<OrderProductDetailDTO> products) { this.products = products; }

    // Método para calcular el total de la orden
    public BigDecimal getTotal() {
        return products.stream()
                .map(OrderProductDetailDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}