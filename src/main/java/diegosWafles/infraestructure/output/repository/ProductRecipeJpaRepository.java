package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.ProductRecipeEntity;
import diegosWafles.infraestructure.output.entity.ProductRecipeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRecipeJpaRepository extends JpaRepository<ProductRecipeEntity, ProductRecipeID> {
}
