package diegosWafles.application;

import diegosWafles.domain.model.entities.Product;
import diegosWafles.domain.model.entities.ProductRecipe;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.port.output.ProductRepositoryPort;
import diegosWafles.domain.port.output.RecipeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepositoryPort productRepo;
    private final RecipeRepositoryPort recipeRepo;

    public ProductService(ProductRepositoryPort productRepo, RecipeRepositoryPort recipeRepo) {
        this.productRepo = productRepo;
        this.recipeRepo = recipeRepo;
    }

    public Product saveProduct(Product product) {
        // Validaciones
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (product.getProductPrice() == null || product.getProductPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a 0");
        }

        if (product.getProductRecipes() == null || product.getProductRecipes().isEmpty()) {
            throw new IllegalArgumentException("El producto debe tener al menos una receta");
        }

        // Validar que todas las recetas existan
        for (ProductRecipe pr : product.getProductRecipes()) {
            Recipe recipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                    .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + pr.getRecipe().getRecipeID()));
            pr.setRecipe(recipe);
        }

        return productRepo.saveProductWithRecipes(product);
    }

    public Product findByID(Integer productID) {
        Product product = productRepo.findByID(productID)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productID));

        // Llenar la información completa de las recetas
        for (ProductRecipe pr : product.getProductRecipes()) {
            Recipe fullRecipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                    .orElse(null);
            if (fullRecipe != null) {
                pr.setRecipe(fullRecipe);
            }
        }

        return product;
    }

    public void updateProduct(Product product) {
        // Validaciones
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (product.getProductPrice() == null || product.getProductPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a 0");
        }

        if (product.getProductRecipes() == null || product.getProductRecipes().isEmpty()) {
            throw new IllegalArgumentException("El producto debe tener al menos una receta");
        }

        // Validar que el producto exista
        productRepo.findByID(product.getProductID())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + product.getProductID()));

        // Validar que todas las recetas existan
        for (ProductRecipe pr : product.getProductRecipes()) {
            Recipe recipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                    .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + pr.getRecipe().getRecipeID()));
            pr.setRecipe(recipe);
        }

        productRepo.updateProductWithRecipes(product);
    }

    public void deleteProduct(Integer productID) {
        Product existing = productRepo.findByID(productID)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productID));
        productRepo.deleteByID(productID);
    }

    public List<Product> findAll() {
        List<Product> products = productRepo.findAll();

        // Llenar la información completa de las recetas
        for (Product product : products) {
            for (ProductRecipe pr : product.getProductRecipes()) {
                Recipe fullRecipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                        .orElse(null);
                if (fullRecipe != null) {
                    pr.setRecipe(fullRecipe);
                }
            }
        }

        return products;
    }
}