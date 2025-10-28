package backend.controller;

import backend.dto.users.UserResponseDTO;
import backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllCommonUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * ✅ Cambia el estado (activo/sancionado) de un usuario por su DPI.
     * @param dpi identificador único del usuario.
     * @param updateRequest contiene el nuevo estado (true o false).
     */
    @PatchMapping("/{dpi}/status")
    public ResponseEntity<UserResponseDTO> toggleUserStatus(
            @PathVariable String dpi,
            @RequestBody StatusUpdateRequest updateRequest) {

        UserResponseDTO updatedUser = userService.updateUserStatus(dpi, updateRequest.getStatus());
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Clase interna para recibir el JSON { "status": true/false }
     */
    public static class StatusUpdateRequest {
        private boolean status;

        public boolean getStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}