package backend.controller;

import backend.dto.loginregister.AuthResponse;
import backend.dto.loginregister.LoginRequest;
import backend.dto.loginregister.RegisterRequest;
import backend.dto.user.UserLoginDTO;
import backend.dto.user.UserRegisterDTO;
import backend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacion")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutSesion(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @GetMapping("/verify-token")
    public ResponseEntity<AuthResponse> verifyToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authenticationService.verifyToken(token));
    }
}