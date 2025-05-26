package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order saveOrderWithProducts(Order order);
    Optional<Order> findByID(Integer orderID);
    List<Order> findAll();
}
