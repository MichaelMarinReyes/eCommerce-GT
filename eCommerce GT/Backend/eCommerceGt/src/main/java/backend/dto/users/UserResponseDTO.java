package backend.dto.users;

import backend.models.users.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private String dpi;
    private String name;
    private String email;
    private String address;
    private boolean status;
    private Role role;
}