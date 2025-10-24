package backend.dto.loginregister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String dpi;
    private String name;
    private String email;
    private String password;
    private String address;
}