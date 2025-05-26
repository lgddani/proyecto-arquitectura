package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.ProviderService;
import diegosWafles.domain.model.dto.ProviderDTO;
import diegosWafles.domain.model.entities.Provider;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProviderDTO> listProviders() {
        return service.listProviders().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProviderDTO createProvider(@RequestBody ProviderDTO dto) {
        Provider provider = toDomain(dto);
        Provider saved = service.saveProvider(provider);
        return toDto(saved);
    }

    @GetMapping("/{providerID}")
    public ProviderDTO getProvider(@PathVariable Integer providerID) {
        return toDto(service.searchByID(providerID));
    }

    @PutMapping("/{providerID}")
    public ProviderDTO updateProvider(@PathVariable Integer providerID, @RequestBody ProviderDTO dto) {
        Provider provider = toDomain(dto);
        provider.setProviderID(providerID);
        return toDto(service.saveProvider(provider));
    }

    @DeleteMapping("/{providerID}")
    public void deleteProvider(@PathVariable Integer providerID) {
        service.deleteProvider(providerID);
    }

    // –– Mappers ––

    private ProviderDTO toDto(Provider provider) {
        return new ProviderDTO(
                provider.getProviderID(),
                provider.getProviderName(),
                provider.getProviderPhone()
        );
    }

    private Provider toDomain(ProviderDTO dto) {
        return new Provider(
                dto.getProviderID(),
                dto.getProviderName(),
                dto.getProviderPhone()
        );
    }
}