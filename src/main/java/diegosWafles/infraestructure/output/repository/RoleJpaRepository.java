package diegosWafles.infraestructure.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import diegosWafles.infraestructure.output.entity.RoleEntity;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
}
