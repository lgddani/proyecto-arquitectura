package diegosWafles.application;

import diegosWafles.domain.model.dto.PasswordUpdateDTO;
import diegosWafles.domain.model.dto.UserListDTO;
import diegosWafles.domain.model.dto.UserUpdateDTO;
import diegosWafles.domain.model.entities.Role;
import diegosWafles.domain.model.entities.User;
import diegosWafles.domain.port.output.RoleRepositoryPort;
import diegosWafles.domain.port.output.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepository,
                       RoleRepositoryPort roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserListDTO> getAllUserSummaries() {
        return userRepository.findAll().stream()
                .map(user -> new UserListDTO(
                        user.getUserName(),
                        user.getUserEmail(),
                        user.getRole().getRolName()
                ))
                .toList();
    }

    public Optional<UserListDTO> getUserSummaryById(Integer id) {
        return userRepository.findById(id)
                .map(user -> new UserListDTO(
                        user.getUserName(),
                        user.getUserEmail(),
                        user.getRole().getRolName()
                ));
    }

    public User updateUser(Integer id, UserUpdateDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Role role = roleRepository.findById(dto.getRolID())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        existing.setUserName(dto.getUserName());
        existing.setUserEmail(dto.getUserEmail());
        existing.setRole(role);

        return userRepository.save(existing);
    }

    public void updateOwnPassword(String email, PasswordUpdateDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getUserPassword())) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }

        if (dto.getNewPassword().length() < 8) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 8 caracteres");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        user.setUserPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

}
