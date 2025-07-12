package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Recipe;
import diegosWafles.domain.model.entities.RecipeIngredient;
import diegosWafles.domain.port.output.RecipeRepositoryPort;
import diegosWafles.infraestructure.output.entity.RecipeEntity;
import diegosWafles.infraestructure.output.entity.RecipeIngredientEntity;
import diegosWafles.infraestructure.output.repository.RecipeIngredientJpaRepository;
import diegosWafles.infraestructure.output.repository.RecipeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecipeRepositoryAdapter implements RecipeRepositoryPort {

    private final RecipeJpaRepository recipeJpaRepository;
    private final RecipeIngredientJpaRepository recipeIngredientJpaRepository;

    public RecipeRepositoryAdapter(RecipeJpaRepository recipeJpaRepository, RecipeIngredientJpaRepository recipeIngredientJpaRepository) {
        this.recipeJpaRepository = recipeJpaRepository;
        this.recipeIngredientJpaRepository = recipeIngredientJpaRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeJpaRepository.findAll().stream()
                .map(recipeEntity -> {
                    List<RecipeIngredientEntity> ingredients = recipeIngredientJpaRepository.findAll().stream()
                            .filter(r -> r.getRecipeID().equals(recipeEntity.getRecipeID()))
                            .collect(Collectors.toList());

                    List<RecipeIngredient> recipeIngredients = ingredients.stream()
                            .map(e -> new RecipeIngredient(
                                    e.getRecipeID(),
                                    new Ingredient(e.getIngredientID(), null, null, null, null, null),
                                    e.getRequiredQuantity()
                            )).collect(Collectors.toList());

                    return new Recipe(recipeEntity.getRecipeID(), recipeEntity.getRecipeName(), recipeIngredients);
                }).collect(Collectors.toList());
    }


    @Override
    public Recipe saveRecipeWithIngredients(Recipe recipe) {
        RecipeEntity entity = new RecipeEntity();
        entity.setRecipeName(recipe.getRecipeName());
        RecipeEntity saved = recipeJpaRepository.save(entity);

        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            RecipeIngredientEntity rel = new RecipeIngredientEntity();
            rel.setRecipeID(saved.getRecipeID());
            rel.setIngredientID(ri.getIngredient().getIngredientID());
            rel.setRequiredQuantity(ri.getRequiredQuantity());
            recipeIngredientJpaRepository.save(rel);
        }

        recipe.setRecipeID(saved.getRecipeID());
        return recipe;
    }

    @Override
    public Optional<Recipe> findByID(Integer recipeID) {
        Optional<RecipeEntity> recipeOpt = recipeJpaRepository.findById(recipeID);
        if (recipeOpt.isEmpty()) return Optional.empty();

        RecipeEntity recipeEntity = recipeOpt.get();
        List<RecipeIngredientEntity> ingredients = recipeIngredientJpaRepository.findAll().stream()
                .filter(r -> r.getRecipeID().equals(recipeID))
                .collect(Collectors.toList());

        List<RecipeIngredient> recipeIngredients = ingredients.stream()
                .map(e -> new RecipeIngredient(
                        e.getRecipeID(),
                        new Ingredient(e.getIngredientID(), null, null, null, null, null),
                        e.getRequiredQuantity()
                )).collect(Collectors.toList());

        return Optional.of(new Recipe(recipeEntity.getRecipeID(), recipeEntity.getRecipeName(), recipeIngredients));
    }

    @Override
    public void updateRecipeWithIngredients(Recipe recipe) {
        // 1. Actualizar nombre de receta
        RecipeEntity entity = recipeJpaRepository.findById(recipe.getRecipeID())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        entity.setRecipeName(recipe.getRecipeName());
        recipeJpaRepository.save(entity);

        // 2. Eliminar ingredientes existentes
        List<RecipeIngredientEntity> existing = recipeIngredientJpaRepository.findAll().stream()
                .filter(r -> r.getRecipeID().equals(recipe.getRecipeID()))
                .collect(Collectors.toList());

        recipeIngredientJpaRepository.deleteAll(existing);

        // 3. Insertar los nuevos (o mismos) ingredientes
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            RecipeIngredientEntity rel = new RecipeIngredientEntity();
            rel.setRecipeID(recipe.getRecipeID());
            rel.setIngredientID(ri.getIngredient().getIngredientID());
            rel.setRequiredQuantity(ri.getRequiredQuantity());
            recipeIngredientJpaRepository.save(rel);
        }
    }

    @Override
    public void deleteByID(Integer recipeID) {
        // Eliminar ingredientes asociados primero
        List<RecipeIngredientEntity> related = recipeIngredientJpaRepository.findAll().stream()
                .filter(r -> r.getRecipeID().equals(recipeID))
                .collect(Collectors.toList());
        recipeIngredientJpaRepository.deleteAll(related);

        // Eliminar receta
        recipeJpaRepository.deleteById(recipeID);
    }
}