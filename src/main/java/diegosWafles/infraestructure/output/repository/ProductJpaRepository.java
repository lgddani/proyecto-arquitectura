package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {
}
