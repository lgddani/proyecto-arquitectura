package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.OrderService;
import diegosWafles.domain.model.dto.OrderDTO;
import diegosWafles.domain.model.dto.OrderDetailDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Order;
import diegosWafles.domain.model.entities.OrderProduct;
import diegosWafles.domain.model.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO dto) {
        try {
            Order order = toDomain(dto);
            Order saved = service.createOrder(order);
            OrderDetailDTO savedDTO = toDetailDto(saved);

            return ResponseHandler.generateResponse(
                    "Orden creada exitosamente",
                    true,
                    savedDTO
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al crear orden",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al crear orden",
                    e.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<Object> listOrders() {
        try {
            List<Order> orders = service.findAll();
            List<OrderDetailDTO> ordersDTO = orders.stream()
                    .map(this::toDetailDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Órdenes consultadas exitosamente",
                    true,
                    ordersDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar las órdenes",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{orderID}")
    public ResponseEntity<Object> getOrder(@PathVariable Integer orderID) {
        try {
            Order order = service.findByID(orderID);
            OrderDetailDTO orderDTO = toDetailDto(order);

            return ResponseHandler.generateResponse(
                    "Orden consultada exitosamente",
                    true,
                    orderDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Orden no encontrada",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar la orden",
                    e.getMessage()
            );
        }
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

    private OrderDetailDTO toDetailDto(Order order) {
        List<OrderDetailDTO.OrderProductDetailDTO> products = order.getOrderProducts().stream()
                .map(op -> {
                    BigDecimal subtotal = op.getProduct().getProductPrice()
                            .multiply(BigDecimal.valueOf(op.getQuantity()));

                    return new OrderDetailDTO.OrderProductDetailDTO(
                            op.getProduct().getProductID(),
                            op.getProduct().getProductName(),
                            op.getProduct().getProductPrice(),
                            op.getQuantity(),
                            subtotal
                    );
                }).collect(Collectors.toList());

        return new OrderDetailDTO(
                order.getOrderID(),
                order.getCreatedAt(),
                order.getComment(),
                products
        );
    }
}