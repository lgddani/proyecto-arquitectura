package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {
}
