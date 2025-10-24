package backend.controller;

import backend.dto.loginregister.AuthResponseDTO;
import backend.dto.loginregister.LoginRequestDTO;
import backend.dto.users.UserLoginDTO;
import backend.dto.users.UserRegisterDTO;
import backend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRegisterDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(new UserLoginDTO(request.getEmail(), request.getPassword())));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutSesion(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @GetMapping("/verify-token")
    public ResponseEntity<AuthResponseDTO> verifyToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authenticationService.verifyToken(token));
    }
}