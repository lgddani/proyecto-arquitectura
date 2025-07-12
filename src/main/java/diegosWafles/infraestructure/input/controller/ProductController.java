package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.ProductService;
import diegosWafles.domain.model.dto.ProductDTO;
import diegosWafles.domain.model.dto.ProductDetailDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Product;
import diegosWafles.domain.model.entities.ProductRecipe;
import diegosWafles.domain.model.entities.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO dto) {
        try {
            Product product = toDomain(dto);
            Product saved = service.saveProduct(product);
            ProductDetailDTO savedDTO = toDetailDto(saved);

            return ResponseHandler.generateResponse(
                    "Producto creado exitosamente",
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
                    "Error al crear producto",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al crear producto",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{productID}")
    public ResponseEntity<Object> getProduct(@PathVariable Integer productID) {
        try {
            Product product = service.findByID(productID);
            ProductDetailDTO productDTO = toDetailDto(product);

            return ResponseHandler.generateResponse(
                    "Producto consultado exitosamente",
                    true,
                    productDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Producto no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar el producto",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{productID}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer productID, @RequestBody ProductDTO dto) {
        try {
            Product product = toDomain(dto);
            product.setProductID(productID);
            service.updateProduct(product);

            Product updated = service.findByID(productID);
            ProductDetailDTO updatedDTO = toDetailDto(updated);

            return ResponseHandler.generateResponse(
                    "Producto actualizado exitosamente",
                    true,
                    updatedDTO
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al actualizar producto",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al actualizar producto",
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Integer productID) {
        try {
            service.deleteProduct(productID);

            return ResponseHandler.generateResponse(
                    "Producto eliminado exitosamente",
                    true
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Producto no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al eliminar producto",
                    e.getMessage()
            );
        }
    }

    @GetMapping
    public ResponseEntity<Object> listProducts() {
        try {
            List<Product> products = service.findAll();
            List<ProductDetailDTO> productsDTO = products.stream()
                    .map(this::toDetailDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Productos consultados exitosamente",
                    true,
                    productsDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar los productos",
                    e.getMessage()
            );
        }
    }

    // –– MAPPERS ––

    private ProductDetailDTO toDetailDto(Product product) {
        List<ProductDetailDTO.ProductRecipeDetailDTO> recipes = product.getProductRecipes().stream()
                .map(pr -> new ProductDetailDTO.ProductRecipeDetailDTO(
                        pr.getRecipe().getRecipeID(),
                        pr.getRecipe().getRecipeName()
                )).collect(Collectors.toList());

        return new ProductDetailDTO(
                product.getProductID(),
                product.getProductName(),
                product.getProductPrice(),
                recipes
        );
    }

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
}