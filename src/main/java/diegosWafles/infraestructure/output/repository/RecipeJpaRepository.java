package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Integer> {
}
