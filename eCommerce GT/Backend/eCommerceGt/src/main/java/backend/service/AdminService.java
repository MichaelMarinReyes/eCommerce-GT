package backend.service;

import backend.dto.users.UserDTO;
import backend.models.users.User;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .dpi(user.getDpi())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .status(user.isStatus())
                .roleName(user.getRole() != null ? user.getRole().getNameRole() : null)
                .build();
    }

    /**
     * Agregar un nuevo empleado
     */
    public UserDTO addEmployee(User user) {
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    /**
     * Actualizar información de un empleado
     */
    public UserDTO updateEmployee(User user) {
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    /**
     * Obtener historial de empleados (todos los que no son administradores)
     */
    public List<UserDTO> getEmployeeHistory() {
        return userRepository.findByRole_NameRoleNot("ADMINISTRADOR")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Eliminar un empleado
     */
    public void deleteEmployee(String dpi) {
        userRepository.deleteById(dpi);
    }
}