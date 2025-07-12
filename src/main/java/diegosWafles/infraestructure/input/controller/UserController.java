package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.UserService;
import diegosWafles.domain.model.dto.*;
import diegosWafles.domain.model.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserListDTO> users = userService.getAllUserSummaries();

            return ResponseHandler.generateResponse(
                    "Usuarios consultados exitosamente",
                    true,
                    users
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar los usuarios",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);
            UserDetailDTO userDetail = toDetailDto(user);

            return ResponseHandler.generateResponse(
                    "Usuario consultado exitosamente",
                    true,
                    userDetail
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Usuario no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar el usuario",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO dto) {
        try {
            User updated = userService.updateUser(id, dto);
            UserDetailDTO userDetail = toDetailDto(updated);

            return ResponseHandler.generateResponse(
                    "Usuario actualizado exitosamente",
                    true,
                    userDetail
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error al actualizar usuario",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno al actualizar usuario",
                    e.getMessage()
            );
        }
    }

    @PutMapping("/me/password")
    @PreAuthorize("hasAuthority('Vendedor') or hasAuthority('Administrador')")
    public ResponseEntity<Object> updateOwnPassword(@RequestBody PasswordUpdateDTO dto, Authentication auth) {
        try {
            String email = auth.getName(); // lo extrae del token
            userService.updateOwnPassword(email, dto);

            return ResponseHandler.generateResponse(
                    "Contraseña actualizada exitosamente",
                    true
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al actualizar contraseña",
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteById(id);

            return ResponseHandler.generateResponse(
                    "Usuario eliminado exitosamente",
                    true
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Usuario no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al eliminar usuario",
                    e.getMessage()
            );
        }
    }

    // Mappers
    private UserDetailDTO toDetailDto(User user) {
        return new UserDetailDTO(
                user.getUserID(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhone(),
                user.isUserStatus(),
                user.getRole().getRolID(),
                user.getRole().getRolName()
        );
    }
}