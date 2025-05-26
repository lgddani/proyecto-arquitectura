package diegosWafles.infraestructure.output.adapter;

import org.springframework.stereotype.Component;
import diegosWafles.domain.model.entities.Role;
import diegosWafles.domain.port.output.RoleRepositoryPort;
import diegosWafles.infraestructure.output.entity.RoleEntity;
import diegosWafles.infraestructure.output.repository.RoleJpaRepository;

import java.util.Optional;

@Component
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryAdapter(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<Role> findById(Integer rolID) {
        Optional<RoleEntity> entityOpt = roleJpaRepository.findById(rolID);
        return entityOpt.map(this::toDomain);
    }

    private Role toDomain(RoleEntity entity) {
        return new Role(entity.getRolID(), entity.getRolName());
    }
}