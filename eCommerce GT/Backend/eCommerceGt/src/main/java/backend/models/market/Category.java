package backend.models.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Long idCategory;
    @Column(name = "category_name", nullable = false)
    private String categoryName;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "idCategory")
    @JsonIgnore
    private List<Product> products;
}
