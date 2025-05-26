package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.ProductService;
import diegosWafles.domain.model.dto.ProductDTO;
import diegosWafles.domain.model.entities.Product;
import diegosWafles.domain.model.entities.ProductRecipe;
import diegosWafles.domain.model.entities.Recipe;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public String createProduct(@RequestBody ProductDTO dto) {
        Product product = toDomain(dto);
        Product saved = service.saveProduct(product);
        return "Product created with ID: " + saved.getProductID();
    }

    @GetMapping("/{productID}")
    public ProductDTO getProduct(@PathVariable Integer productID) {
        return toDto(service.findByID(productID));
    }

    @PutMapping("/{productID}")
    public String updateProduct(@PathVariable Integer productID, @RequestBody ProductDTO dto) {
        Product product = toDomain(dto);
        product.setProductID(productID);
        service.updateProduct(product);
        return "Product updated successfully.";
    }

    @DeleteMapping("/{productID}")
    public String deleteProduct(@PathVariable Integer productID) {
        service.deleteProduct(productID);
        return "Product deleted successfully.";
    }

    @GetMapping
    public List<ProductDTO> listProducts() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // –– MAPPERS ––

    private Product toDomain(ProductDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductPrice(dto.getProductPrice());

        List<ProductRecipe> recipes = dto.getRecipeIDs().stream()
                .map(id -> new ProductRecipe(null, new Recipe(id, null, null)))
                .collect(Collectors.toList());

        product.setProductRecipes(recipes);
        return product;
    }

    private ProductDTO toDto(Product product) {
        return new ProductDTO(
                product.getProductID(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductRecipes().stream()
                        .map(pr -> pr.getRecipe().getRecipeID())
                        .collect(Collectors.toList())
        );
    }
}
