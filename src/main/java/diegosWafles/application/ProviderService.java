package diegosWafles.application;

import diegosWafles.domain.model.entities.Provider;
import diegosWafles.domain.port.output.ProviderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepositoryPort providerRepo;

    public ProviderService(ProviderRepositoryPort providerRepo) {
        this.providerRepo = providerRepo;
    }

    public List<Provider> listProviders() {
        return providerRepo.findAllProviders();
    }

    public Provider searchByID(Integer providerID) {
        return providerRepo.findProviderByID(providerID)
                .orElseThrow(() -> new RuntimeException("No existe el proveedor con ID: " + providerID));
    }

    public Provider saveProvider(Provider provider) {
        return providerRepo.saveProviderByID(provider);
    }

    public void deleteProvider(Integer providerID) {
        providerRepo.deleteProviderByID(providerID);
    }
}
