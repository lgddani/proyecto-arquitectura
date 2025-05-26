package diegosWafles.infraestructure.output.adapter;

import diegosWafles.domain.model.entities.Role;
import diegosWafles.domain.model.entities.User;
import diegosWafles.domain.port.output.UserRepositoryPort;
import diegosWafles.infraestructure.output.entity.RoleEntity;
import diegosWafles.infraestructure.output.entity.UserEntity;
import diegosWafles.infraestructure.output.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final RoleRepositoryAdapter roleRepositoryAdapter;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, RoleRepositoryAdapter roleRepositoryAdapter) {
        this.userJpaRepository = userJpaRepository;
        this.roleRepositoryAdapter = roleRepositoryAdapter;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByUserEmail(email)
                .map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return toDomain(saved);
    }

    private User toDomain(UserEntity e) {
        Role roleDomain = roleRepositoryAdapter.findById(e.getRole().getRolID())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return new User(e.getUserID(), e.getUserName(), e.getUserEmail(), e.getUserPassword(), e.isUserStatus(), roleDomain);
    }

    private UserEntity toEntity(User u) {
        RoleEntity roleEntity = new RoleEntity(u.getRole().getRolID(), u.getRole().getRolName());
        return new UserEntity(u.getUserID(), u.getUserName(), u.getUserEmail(), u.getUserPassword(), u.isUserStatus(), roleEntity);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        userJpaRepository.deleteById(id);
    }

}