package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.model.entities.Provider;
import diegosWafles.domain.port.output.ProviderRepositoryPort;
import diegosWafles.infraestructure.output.entity.ProviderEntity;
import diegosWafles.infraestructure.output.repository.ProviderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProviderRepositoryAdapter implements ProviderRepositoryPort {

    private final ProviderJpaRepository jpaRepo;

    public ProviderRepositoryAdapter(ProviderJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Provider> findAllProviders() {
        return jpaRepo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Provider> findProviderByID(Integer providerID) {
        return jpaRepo.findById(providerID).map(this::toDomain);
    }

    @Override
    public Provider saveProviderByID(Provider provider) {
        ProviderEntity entity = toEntity(provider);
        ProviderEntity saved = jpaRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteProviderByID(Integer providerID) {
        jpaRepo.deleteById(providerID);
    }

    private Provider toDomain(ProviderEntity entity) {
        return new Provider(
                entity.getProviderID(),
                entity.getProviderName(),
                entity.getProviderPhone()
        );
    }

    private ProviderEntity toEntity(Provider provider) {
        ProviderEntity entity = new ProviderEntity();
        entity.setProviderID(provider.getProviderID());
        entity.setProviderName(provider.getProviderName());
        entity.setProviderPhone(provider.getProviderPhone());
        return entity;
    }
}
