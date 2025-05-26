package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.Role;
import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findById(Integer rolID);
}
