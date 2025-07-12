package diegosWafles.infraestructure.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import diegosWafles.domain.model.entities.Role;
import diegosWafles.domain.model.entities.User;
import diegosWafles.domain.port.input.AuthServicePort;
import diegosWafles.domain.port.output.RoleRepositoryPort;
import diegosWafles.domain.port.output.UserRepositoryPort;
import diegosWafles.infraestructure.security.JwtUtils;
import diegosWafles.domain.model.dto.UserRegisterDTO;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthServicePort {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepositoryPort userRepository, RoleRepositoryPort roleRepository,
                           PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return jwtUtils.generateJwtToken(user.getUserEmail());
    }

    @Override
    public User register(UserRegisterDTO dto) {
        if (!dto.getUserPassword().equals(dto.getUserConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (dto.getUserPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        if (userRepository.findByEmail(dto.getUserEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Role role = roleRepository.findById(dto.getRolID())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User newUser = new User();
        newUser.setUserName(dto.getUserName());
        newUser.setUserEmail(dto.getUserEmail());
        newUser.setUserPhone(dto.getUserPhone());
        newUser.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        newUser.setUserStatus(true);
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

}