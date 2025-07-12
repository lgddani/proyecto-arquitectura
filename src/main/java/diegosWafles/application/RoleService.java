package diegosWafles.application;

import diegosWafles.domain.model.entities.Role;
import diegosWafles.domain.port.output.RoleRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepositoryPort roleRepository;

    public RoleService(RoleRepositoryPort roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Integer rolID) {
        return roleRepository.findById(rolID)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolID));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}