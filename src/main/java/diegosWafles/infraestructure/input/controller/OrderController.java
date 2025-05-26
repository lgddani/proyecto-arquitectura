package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.OrderService;
import diegosWafles.domain.model.dto.OrderDTO;
import diegosWafles.domain.model.entities.Order;
import diegosWafles.domain.model.entities.OrderProduct;
import diegosWafles.domain.model.entities.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderDTO dto) {
        Order order = toDomain(dto);
        Order saved = service.createOrder(order);
        return "Order registered with ID: " + saved.getOrderID();
    }

    @GetMapping
    public List<OrderDTO> listOrders() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderID}")
    public OrderDTO getOrder(@PathVariable Integer orderID) {
        return toDto(service.findByID(orderID));
    }

    // –– MAPPERS ––

    private Order toDomain(OrderDTO dto) {
        Order order = new Order();
        order.setComment(dto.getComment());

        List<OrderProduct> products = dto.getProducts().stream()
                .map(p -> new OrderProduct(null, new Product(p.getProductID(), null, null, null), p.getQuantity()))
                .collect(Collectors.toList());

        order.setOrderProducts(products);
        return order;
    }

    private OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setComment(order.getComment());

        List<OrderDTO.OrderProductDTO> products = order.getOrderProducts().stream()
                .map(op -> {
                    OrderDTO.OrderProductDTO dtoItem = new OrderDTO.OrderProductDTO();
                    dtoItem.setProductID(op.getProduct().getProductID());
                    dtoItem.setQuantity(op.getQuantity());
                    return dtoItem;
                }).collect(Collectors.toList());

        dto.setProducts(products);
        return dto;
    }
}
