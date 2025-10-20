package backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginDTO {
    private String email;
    private String password;
}