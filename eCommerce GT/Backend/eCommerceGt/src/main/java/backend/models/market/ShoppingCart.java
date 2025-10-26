package backend.models.market;

import backend.models.users.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart", nullable = false)
    private Long idCart;
    @ManyToOne
    @JoinColumn(name = "user_dpi", nullable = false)
    private User userDpi;
    @Column(name = "status", nullable = false)
    private boolean status; // en curso o entregado
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "update_at", nullable = false)
    private Date updateAt;
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartProduct> products;
}
