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
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + providerID));
    }

    public Provider saveProvider(Provider provider) {
        if (provider.getProviderName() == null || provider.getProviderName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor es obligatorio");
        }
        return providerRepo.saveProviderByID(provider);
    }

    public void deleteProvider(Integer providerID) {
        Provider existing = providerRepo.findProviderByID(providerID)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + providerID));
        providerRepo.deleteProviderByID(providerID);
    }
}