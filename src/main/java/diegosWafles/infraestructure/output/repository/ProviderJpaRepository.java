package diegosWafles.infraestructure.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import diegosWafles.infraestructure.output.entity.ProviderEntity;

public interface ProviderJpaRepository extends JpaRepository<ProviderEntity, Integer> {
}