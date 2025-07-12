package diegosWafles.infraestructure.input.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diegosWafles.domain.model.dto.LoginRequestDTO;
import diegosWafles.domain.model.dto.LoginResponseDTO;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.entities.User;
import diegosWafles.domain.port.input.AuthServicePort;
import diegosWafles.domain.model.dto.UserRegisterDTO;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServicePort authService;

    public AuthController(AuthServicePort authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO loginResponse = authService.login(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            return ResponseHandler.generateResponse(
                    "Acceso exitoso, bienvenido",
                    true,
                    loginResponse
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de autenticación",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error interno del servidor",
                    e.getMessage()
            );
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User newUser = authService.register(userRegisterDTO);

            return ResponseHandler.generateResponse(
                    "Usuario registrado exitosamente",
                    true,
                    newUser
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Error de validación",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al registrar usuario",
                    e.getMessage()
            );
        }
    }
}