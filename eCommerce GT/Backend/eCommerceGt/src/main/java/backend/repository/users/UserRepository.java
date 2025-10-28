package backend.repository.users;

import backend.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByDpi(String dpi);

    Optional<User> findByEmail(String email);

    List<User> findByRole_NameRoleIgnoreCase(String roleName);

    List<User> findByRole_NameRoleNot(String roleName);

}