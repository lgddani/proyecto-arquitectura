package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.UserService;
import diegosWafles.domain.model.dto.PasswordUpdateDTO;
import diegosWafles.domain.model.dto.UserUpdateDTO;
import diegosWafles.domain.model.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import diegosWafles.domain.model.dto.UserListDTO;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserListDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUserSummaries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserListDTO> getUserById(@PathVariable Integer id) {
        return userService.getUserSummaryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO dto) {
        try {
            User updated = userService.updateUser(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/me/password")
    @PreAuthorize("hasAuthority('Vendedor')")
    public ResponseEntity<?> updateOwnPassword(@RequestBody PasswordUpdateDTO dto, Authentication auth) {
        String email = auth.getName(); // lo extrae del token
        try {
            userService.updateOwnPassword(email, dto);
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
