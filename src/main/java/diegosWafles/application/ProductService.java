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
        for (ProductRecipe pr : product.getProductRecipes()) {
            Recipe recipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                    .orElseThrow(() -> new RuntimeException("Recipe not found: " + pr.getRecipe().getRecipeID()));
            pr.setRecipe(recipe);
        }
        return productRepo.saveProductWithRecipes(product);
    }

    public Product findByID(Integer productID) {
        return productRepo.findByID(productID)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productID));
    }

    public void updateProduct(Product product) {
        for (ProductRecipe pr : product.getProductRecipes()) {
            Recipe recipe = recipeRepo.findByID(pr.getRecipe().getRecipeID())
                    .orElseThrow(() -> new RuntimeException("Recipe not found: " + pr.getRecipe().getRecipeID()));
            pr.setRecipe(recipe);
        }
        productRepo.updateProductWithRecipes(product);
    }

    public void deleteProduct(Integer productID) {
        productRepo.deleteByID(productID);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }
}
