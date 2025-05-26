package diegosWafles.infraestructure.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import diegosWafles.infraestructure.output.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserEmail(String userEmail);
}
