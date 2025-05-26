package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.RecipeIngredientEntity;
import diegosWafles.infraestructure.output.entity.RecipeIngredientID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientJpaRepository extends JpaRepository<RecipeIngredientEntity, RecipeIngredientID> {
}
