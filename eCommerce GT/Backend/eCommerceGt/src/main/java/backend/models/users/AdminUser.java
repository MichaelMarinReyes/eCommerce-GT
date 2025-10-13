package backend.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AdminUser extends User {
    public void newEmployee() {

    }

    public void viewReports() {}
}
