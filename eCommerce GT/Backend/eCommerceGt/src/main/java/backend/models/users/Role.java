package backend.models.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Long idRole;
    @Column(name = "name_role", nullable = false)
    private String nameRole;
    @Column(name = "description", nullable = false, unique = true)
    private String description;
    @OneToMany(mappedBy = "role")
    List<User> users;
}