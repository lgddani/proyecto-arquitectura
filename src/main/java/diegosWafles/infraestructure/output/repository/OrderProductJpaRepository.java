package diegosWafles.infraestructure.output.repository;

import diegosWafles.infraestructure.output.entity.OrderProductEntity;
import diegosWafles.infraestructure.output.entity.OrderProductID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProductEntity, OrderProductID> {
}
