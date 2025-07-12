package diegosWafles.infraestructure.input.controller;

import diegosWafles.application.RoleService;
import diegosWafles.domain.model.dto.ResponseHandler;
import diegosWafles.domain.model.dto.RoleDTO;
import diegosWafles.domain.model.entities.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        try {
            List<Role> roles = roleService.findAll();
            List<RoleDTO> rolesDTO = roles.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return ResponseHandler.generateResponse(
                    "Roles consultados exitosamente",
                    true,
                    rolesDTO
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar los roles",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{rolID}")
    public ResponseEntity<Object> getRoleById(@PathVariable Integer rolID) {
        try {
            Role role = roleService.findById(rolID);
            RoleDTO roleDTO = toDto(role);

            return ResponseHandler.generateResponse(
                    "Rol consultado exitosamente",
                    true,
                    roleDTO
            );
        } catch (RuntimeException e) {
            return ResponseHandler.generateNotFoundResponse(
                    "Rol no encontrado",
                    e.getMessage()
            );
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(
                    "Error al consultar el rol",
                    e.getMessage()
            );
        }
    }

    // Mapper
    private RoleDTO toDto(Role role) {
        return new RoleDTO(role.getRolID(), role.getRolName());
    }
}