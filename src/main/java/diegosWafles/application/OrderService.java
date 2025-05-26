package diegosWafles.application;

import diegosWafles.domain.model.entities.Order;
import diegosWafles.domain.port.output.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepositoryPort orderRepo;

    public OrderService(OrderRepositoryPort orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order createOrder(Order order) {
        return orderRepo.saveOrderWithProducts(order);
    }

    public Order findByID(Integer orderID) {
        return orderRepo.findByID(orderID)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderID));
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
