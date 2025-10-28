package backend.dto.users;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String dpi;
    private String name;
    private String email;
    private String address;
    private boolean status;
    private String roleName;
}
