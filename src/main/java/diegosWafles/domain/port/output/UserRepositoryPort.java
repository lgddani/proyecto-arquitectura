package diegosWafles.domain.port.output;

import diegosWafles.domain.model.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
    List<User> findAll();
    User save(User user);
    void deleteById(Integer id);
}
