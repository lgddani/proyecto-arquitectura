package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.ProviderService;
import diegosWafles.domain.model.dto.ProviderDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> listProviders() {
        try {
            List<Provider> providers = service.listProviders();
            List<ProviderDTO> providersDTO = providers.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Proveedores consultados exitosamente",
                    true,
                    providersDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar los proveedores",
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProvider(@RequestBody ProviderDTO dto) {
        try {
            Provider provider = toDomain(dto);
            Provider saved = service.saveProvider(provider);
            ProviderDTO savedDTO = toDto(saved);

            return ResponseHandler.generateResponse(
                    "Proveedor creado exitosamente",
                    true,
                    savedDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al crear el proveedor",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{providerID}")
    public ResponseEntity<Object> getProvider(@PathVariable Integer providerID) {
        try {
            Provider provider = service.searchByID(providerID);
            ProviderDTO providerDTO = toDto(provider);

            return ResponseHandler.generateResponse(
                    "Proveedor consultado exitosamente",
                    true,
                    providerDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Proveedor no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar el proveedor",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{providerID}")
    public ResponseEntity<Object> updateProvider(@PathVariable Integer providerID, @RequestBody ProviderDTO dto) {
        try {
            Provider provider = toDomain(dto);
            provider.setProviderID(providerID);
            Provider updated = service.saveProvider(provider);
            ProviderDTO updatedDTO = toDto(updated);

            return ResponseHandler.generateResponse(
                    "Proveedor actualizado exitosamente",
                    true,
                    updatedDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al actualizar proveedor",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al actualizar proveedor",
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{providerID}")
    public ResponseEntity<Object> deleteProvider(@PathVariable Integer providerID) {
        try {
            service.deleteProvider(providerID);

            return ResponseHandler.generateResponse(
                    "Proveedor eliminado exitosamente",
                    true
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Proveedor no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al eliminar proveedor",
                    e.getMessage()
            );
        }
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