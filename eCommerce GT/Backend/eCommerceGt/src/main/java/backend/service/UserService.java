package backend.service;

import backend.dto.users.UserResponseDTO;
import backend.models.users.User;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllCommonUsers() {
        return userRepository.findByRole_NameRoleIgnoreCase("USUARIO COMÚN")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getDpi(), user.getName(), user.getEmail(), user.getAddress(), user.isStatus(), user.getRole());
    }

    public UserResponseDTO updateUserStatus(String dpi, boolean newStatus) {
        return userRepository.findById(dpi)
                .map(user -> {
                    user.setStatus(newStatus);
                    User savedUser = userRepository.save(user);
                    return toDTO(savedUser);
                })
                .orElse(null);
    }
}