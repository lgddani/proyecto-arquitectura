package diegosWafles.infraestructure.output.adapter;

import diegosWafles.application.NotificationService;
import diegosWafles.domain.model.entities.*;
import diegosWafles.domain.port.output.OrderRepositoryPort;
import diegosWafles.infraestructure.output.entity.*;
import diegosWafles.infraestructure.output.repository.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderRepo;
    private final OrderProductJpaRepository orderProductRepo;
    private final ProductJpaRepository productRepo;
    private final ProductRecipeJpaRepository productRecipeRepo;
    private final RecipeIngredientJpaRepository recipeIngredientRepo;
    private final IngredientJpaRepository ingredientRepo;
    private final NotificationService notificationService;
    private final IngredientRepositoryAdapter ingredientAdapter;

    public OrderRepositoryAdapter(
            OrderJpaRepository orderRepo,
            OrderProductJpaRepository orderProductRepo,
            ProductJpaRepository productRepo,
            ProductRecipeJpaRepository productRecipeRepo,
            RecipeIngredientJpaRepository recipeIngredientRepo,
            IngredientJpaRepository ingredientRepo,
            NotificationService notificationService,
            IngredientRepositoryAdapter ingredientAdapter
    ) {
        this.orderRepo = orderRepo;
        this.orderProductRepo = orderProductRepo;
        this.productRepo = productRepo;
        this.productRecipeRepo = productRecipeRepo;
        this.recipeIngredientRepo = recipeIngredientRepo;
        this.ingredientRepo = ingredientRepo;
        this.notificationService = notificationService;
        this.ingredientAdapter = ingredientAdapter;
    }

    @Override
    public Order saveOrderWithProducts(Order order) {
        // Paso 1: calcular ingredientes requeridos totales
        Map<Integer, Double> requiredIngredients = new HashMap<>();

        for (OrderProduct op : order.getOrderProducts()) {
            Integer productID = op.getProduct().getProductID();
            Integer quantity = op.getQuantity();

            List<Integer> recipeIDs = productRecipeRepo.findAll().stream()
                    .filter(r -> r.getProductID().equals(productID))
                    .map(ProductRecipeEntity::getRecipeID)
                    .toList();

            for (Integer recipeID : recipeIDs) {
                List<RecipeIngredientEntity> ingredientes = recipeIngredientRepo.findAll().stream()
                        .filter(i -> i.getRecipeID().equals(recipeID))
                        .toList();

                for (RecipeIngredientEntity ri : ingredientes) {
                    Integer ingID = ri.getIngredientID();
                    double cantidad = ri.getRequiredQuantity().doubleValue() * quantity;
                    requiredIngredients.merge(ingID, cantidad, Double::sum);
                }
            }
        }

        // Paso 2: verificar stock disponible
        List<IngredientEntity> ingredientsInDB = ingredientRepo.findAll();
        List<String> missing = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : requiredIngredients.entrySet()) {
            IngredientEntity ing = ingredientsInDB.stream()
                    .filter(i -> i.getIngredientID().equals(entry.getKey()))
                    .findFirst()
                    .orElse(null);

            if (ing == null || ing.getIngredientQuantity().doubleValue() < entry.getValue()) {
                String name = ing != null ? ing.getIngredientName() : "ID " + entry.getKey();
                missing.add(name + " (necesita " + entry.getValue() + ")");
            }
        }

        if (!missing.isEmpty()) {
            throw new RuntimeException("Ingredientes insuficientes: " + String.join(", ", missing));
        }

        // Paso 3: guardar orden
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setComment(order.getComment());
        orderEntity.setCreatedAt(LocalDateTime.now());
        OrderEntity saved = orderRepo.save(orderEntity);

        // Paso 4: guardar productos y actualizar stock
        for (OrderProduct op : order.getOrderProducts()) {
            OrderProductEntity opEntity = new OrderProductEntity();
            opEntity.setOrderID(saved.getOrderID());
            opEntity.setProductID(op.getProduct().getProductID());
            opEntity.setQuantity(op.getQuantity());
            orderProductRepo.save(opEntity);
        }

        // Paso 5: descontar ingredientes
        List<Integer> affectedIngredientIds = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : requiredIngredients.entrySet()) {
            IngredientEntity ing = ingredientsInDB.stream()
                    .filter(i -> i.getIngredientID().equals(entry.getKey()))
                    .findFirst()
                    .get();

            ing.setIngredientQuantity(ing.getIngredientQuantity().subtract(
                    new java.math.BigDecimal(entry.getValue())
            ));

            ingredientRepo.save(ing);
            affectedIngredientIds.add(ing.getIngredientID());
        }

        // Paso 6: verificar ingredientes con stock bajo y notificar
        List<Ingredient> updatedIngredients = affectedIngredientIds.stream()
                .map(id -> ingredientAdapter.findIngredientByID(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        notificationService.checkAndNotifyLowStock(updatedIngredients);

        order.setOrderID(saved.getOrderID());
        order.setCreatedAt(saved.getCreatedAt());
        return order;
    }

    @Override
    public Optional<Order> findByID(Integer orderID) {
        Optional<OrderEntity> opt = orderRepo.findById(orderID);
        if (opt.isEmpty()) return Optional.empty();

        List<OrderProductEntity> products = orderProductRepo.findAll().stream()
                .filter(p -> p.getOrderID().equals(orderID))
                .toList();

        List<OrderProduct> orderProducts = products.stream().map(p ->
                new OrderProduct(orderID, new Product(p.getProductID(), null, null, null), p.getQuantity())
        ).toList();

        return Optional.of(new Order(orderID, opt.get().getCreatedAt(), opt.get().getComment(), orderProducts));
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll().stream().map(entity -> {
            List<OrderProductEntity> products = orderProductRepo.findAll().stream()
                    .filter(p -> p.getOrderID().equals(entity.getOrderID()))
                    .toList();

            List<OrderProduct> orderProducts = products.stream().map(p ->
                    new OrderProduct(p.getOrderID(), new Product(p.getProductID(), null, null, null), p.getQuantity())
            ).toList();

            return new Order(entity.getOrderID(), entity.getCreatedAt(), entity.getComment(), orderProducts);
        }).toList();
    }
}