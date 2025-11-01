package backend.service;

import backend.dto.loginregister.AuthResponseDTO;
import backend.dto.users.UserLoginDTO;
import backend.dto.users.UserRegisterDTO;
import backend.models.users.Role;
import backend.models.users.User;
import backend.repository.users.RoleRepository;
import backend.repository.users.UserRepository;
import backend.security.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Jwt jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, Jwt jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }
    public AuthResponseDTO register(UserRegisterDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponseDTO.builder()
                    .message("El correo ya está registrado.")
                    .build();
        }

        Role defaultRole = roleRepository.findByNameRole("USUARIO COMÚN")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado."));

        User user = User.builder()
                .dpi(request.getDpi())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .status(true)
                .role(defaultRole)
                .build();

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().getNameRole());

        return AuthResponseDTO.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .message("Usuario registrado exitosamente.")
                .build();
    }

    public AuthResponseDTO login(UserLoginDTO request) {
        var userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return AuthResponseDTO.builder()
                    .message("Usuario no encontrado.")
                    .build();
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        if (!user.isStatus()) {
            return AuthResponseDTO.builder()
                    .message("Usuario suspendido.")
                    .build();
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().getNameRole());

        return AuthResponseDTO.builder()
                .token(token)
                .message("Inicio de sesión exitoso.")
                .name(user.getName())
                .email(user.getEmail())
                .dpi(user.getDpi())
                .role(user.getRole().getNameRole())
                .build();
    }

    public void logout(String token) {
        String cleanToken = token.replace("Bearer ", "");
        jwtUtils.addToBlacklist(cleanToken);
    }

    public AuthResponseDTO verifyToken(String token) {
        String cleanToken = token.replace("Bearer ", "");

        if (!jwtUtils.validateToken(cleanToken)) {
            return AuthResponseDTO.builder()
                    .token(null)
                    .message("Token inválido o expirado.")
                    .build();
        }

        String email = jwtUtils.extractUsername(cleanToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        return AuthResponseDTO.builder()
                .token(cleanToken)
                .name(user.getName())
                .email(user.getEmail())
                .message("Token válido.")
                .build();
    }
}