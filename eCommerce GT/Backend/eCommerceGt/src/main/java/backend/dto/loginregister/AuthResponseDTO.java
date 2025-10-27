package backend.dto.loginregister;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDTO {
    private String token;
    private String message;
    private String name;
    private String email;
    private String dpi;
    private String role;
}