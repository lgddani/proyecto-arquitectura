package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product saveProductWithRecipes(Product product);
    Optional<Product> findByID(Integer productID);
    void updateProductWithRecipes(Product product);
    void deleteByID(Integer productID);
    List<Product> findAll();
}
