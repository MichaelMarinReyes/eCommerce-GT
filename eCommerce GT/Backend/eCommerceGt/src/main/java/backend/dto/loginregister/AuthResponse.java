package backend.dto.loginregister;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private String message;
    private String name;
    private String email;
}