package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity, Integer> {
}
