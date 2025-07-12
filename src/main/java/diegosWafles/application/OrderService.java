package diegosWafles.application;

import diegosWafles.domain.model.entities.Order;
import diegosWafles.domain.model.entities.OrderProduct;
import diegosWafles.domain.model.entities.Product;
import diegosWafles.domain.port.output.OrderRepositoryPort;
import diegosWafles.domain.port.output.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepositoryPort orderRepo;
    private final ProductRepositoryPort productRepo;

    public OrderService(OrderRepositoryPort orderRepo, ProductRepositoryPort productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public Order createOrder(Order order) {
        // Validaciones
        if (order.getOrderProducts() == null || order.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("La orden debe tener al menos un producto");
        }

        // Validar que todos los productos existan y tengan cantidad válida
        for (OrderProduct op : order.getOrderProducts()) {
            if (op.getQuantity() == null || op.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad de productos debe ser mayor a 0");
            }

            Product product = productRepo.findByID(op.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + op.getProduct().getProductID()));
            op.setProduct(product);
        }

        return orderRepo.saveOrderWithProducts(order);
    }

    public Order findByID(Integer orderID) {
        Order order = orderRepo.findByID(orderID)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + orderID));

        // Llenar la información completa de los productos
        for (OrderProduct op : order.getOrderProducts()) {
            Product fullProduct = productRepo.findByID(op.getProduct().getProductID())
                    .orElse(null);
            if (fullProduct != null) {
                op.setProduct(fullProduct);
            }
        }

        return order;
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepo.findAll();

        // Llenar la información completa de los productos
        for (Order order : orders) {
            for (OrderProduct op : order.getOrderProducts()) {
                Product fullProduct = productRepo.findByID(op.getProduct().getProductID())
                        .orElse(null);
                if (fullProduct != null) {
                    op.setProduct(fullProduct);
                }
            }
        }

        return orders;
    }
}