package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Provider;
import java.util.List;
import java.util.Optional;

public interface ProviderRepositoryPort {
    List<Provider> findAllProviders();
    Optional<Provider> findProviderByID(Integer providerID);
    Provider saveProviderByID(Provider provider);
    void deleteProviderByID(Integer providerID);
}
