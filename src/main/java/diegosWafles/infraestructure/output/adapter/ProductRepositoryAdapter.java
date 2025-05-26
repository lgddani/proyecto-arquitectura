package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.model.entities.Product;
import diegosWafles.domain.model.entities.ProductRecipe;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.port.output.ProductRepositoryPort;
import diegosWafles.infraestructure.output.entity.ProductEntity;
import diegosWafles.infraestructure.output.entity.ProductRecipeEntity;
import diegosWafles.infraestructure.output.repository.ProductJpaRepository;
import diegosWafles.infraestructure.output.repository.ProductRecipeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository productRepo;
    private final ProductRecipeJpaRepository productRecipeRepo;

    public ProductRepositoryAdapter(ProductJpaRepository productRepo, ProductRecipeJpaRepository productRecipeRepo) {
        this.productRepo = productRepo;
        this.productRecipeRepo = productRecipeRepo;
    }

    @Override
    public Product saveProductWithRecipes(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(product.getProductName());
        entity.setProductPrice(product.getProductPrice());
        ProductEntity saved = productRepo.save(entity);

        for (ProductRecipe pr : product.getProductRecipes()) {
            ProductRecipeEntity rel = new ProductRecipeEntity();
            rel.setProductID(saved.getProductID());
            rel.setRecipeID(pr.getRecipe().getRecipeID());
            productRecipeRepo.save(rel);
        }

        product.setProductID(saved.getProductID());
        return product;
    }

    @Override
    public Optional<Product> findByID(Integer productID) {
        Optional<ProductEntity> productOpt = productRepo.findById(productID);
        if (productOpt.isEmpty()) return Optional.empty();

        ProductEntity entity = productOpt.get();

        List<ProductRecipeEntity> rels = productRecipeRepo.findAll().stream()
                .filter(r -> r.getProductID().equals(productID))
                .collect(Collectors.toList());

        List<ProductRecipe> productRecipes = rels.stream()
                .map(rel -> new ProductRecipe(
                        rel.getProductID(),
                        new Recipe(rel.getRecipeID(), null, null)
                )).collect(Collectors.toList());

        return Optional.of(new Product(
                entity.getProductID(),
                entity.getProductName(),
                entity.getProductPrice(),
                productRecipes
        ));
    }

    @Override
    public void updateProductWithRecipes(Product product) {
        ProductEntity entity = productRepo.findById(product.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        entity.setProductName(product.getProductName());
        entity.setProductPrice(product.getProductPrice());
        productRepo.save(entity);

        // eliminar recetas anteriores
        List<ProductRecipeEntity> oldRels = productRecipeRepo.findAll().stream()
                .filter(r -> r.getProductID().equals(product.getProductID()))
                .collect(Collectors.toList());
        productRecipeRepo.deleteAll(oldRels);

        // insertar las nuevas
        for (ProductRecipe pr : product.getProductRecipes()) {
            ProductRecipeEntity rel = new ProductRecipeEntity();
            rel.setProductID(product.getProductID());
            rel.setRecipeID(pr.getRecipe().getRecipeID());
            productRecipeRepo.save(rel);
        }
    }

    @Override
    public void deleteByID(Integer productID) {
        List<ProductRecipeEntity> rels = productRecipeRepo.findAll().stream()
                .filter(r -> r.getProductID().equals(productID))
                .collect(Collectors.toList());
        productRecipeRepo.deleteAll(rels);
        productRepo.deleteById(productID);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll().stream().map(entity -> {
            List<ProductRecipeEntity> rels = productRecipeRepo.findAll().stream()
                    .filter(r -> r.getProductID().equals(entity.getProductID()))
                    .collect(Collectors.toList());

            List<ProductRecipe> productRecipes = rels.stream()
                    .map(rel -> new ProductRecipe(
                            rel.getProductID(),
                            new Recipe(rel.getRecipeID(), null, null)
                    )).collect(Collectors.toList());

            return new Product(
                    entity.getProductID(),
                    entity.getProductName(),
                    entity.getProductPrice(),
                    productRecipes
            );
        }).collect(Collectors.toList());
    }
}
