package diegosWafles.infraestructure.output.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderProductID implements Serializable {

    private Integer orderID;
    private Integer productID;

    public OrderProductID() {}

    public OrderProductID(Integer orderID, Integer productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductID that)) return false;
        return Objects.equals(orderID, that.orderID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, productID);
    }
}
