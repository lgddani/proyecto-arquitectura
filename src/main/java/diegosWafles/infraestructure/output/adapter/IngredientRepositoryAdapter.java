package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.model.entities.Ingredient;
import diegosWafles.domain.model.entities.Provider;
import diegosWafles.domain.port.output.IngredientRepositoryPort;
import diegosWafles.infraestructure.output.entity.IngredientEntity;
import diegosWafles.infraestructure.output.entity.ProviderEntity;
import diegosWafles.infraestructure.output.repository.IngredientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IngredientRepositoryAdapter implements IngredientRepositoryPort {

    private final IngredientJpaRepository jpaRepo;
    private final ProviderRepositoryAdapter providerAdapter;

    public IngredientRepositoryAdapter(IngredientJpaRepository jpaRepo, ProviderRepositoryAdapter providerAdapter) {
        this.jpaRepo = jpaRepo;
        this.providerAdapter = providerAdapter;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return jpaRepo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Ingredient> findIngredientByID(Integer ingredientID) {
        return jpaRepo.findById(ingredientID).map(this::toDomain);
    }

    @Override
    public Ingredient saveIngredientByID(Ingredient ingredient) {
        IngredientEntity entity = toEntity(ingredient);
        IngredientEntity saved = jpaRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteIngredientByID(Integer ingredientID) {
        jpaRepo.deleteById(ingredientID);
    }

    private Ingredient toDomain(IngredientEntity entity) {
        Provider provider = providerAdapter.findProviderByID(entity.getProvider().getProviderID()).orElse(null);
        return new Ingredient(
                entity.getIngredientID(),
                entity.getIngredientName(),
                Ingredient.IngredientUnit.valueOf(entity.getIngredientUnit().name()),
                entity.getIngredientQuantity(),
                provider
        );
    }

    private IngredientEntity toEntity(Ingredient ingredient) {
        IngredientEntity entity = new IngredientEntity();
        entity.setIngredientID(ingredient.getIngredientID());
        entity.setIngredientName(ingredient.getIngredientName());
        entity.setIngredientUnit(IngredientEntity.IngredientUnit.valueOf(ingredient.getIngredientUnit().name()));
        entity.setIngredientQuantity(ingredient.getIngredientQuantity());

        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setProviderID(ingredient.getProvider().getProviderID());
        entity.setProvider(providerEntity);

        return entity;
    }
}
