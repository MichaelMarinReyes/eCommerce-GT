package backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDTO {
    private String dpi;
    private String name;
    private String email;
    private String password;
    private String address;
}