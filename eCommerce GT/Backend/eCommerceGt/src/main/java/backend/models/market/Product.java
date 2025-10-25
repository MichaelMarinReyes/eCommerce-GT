package backend.models.market;

import backend.models.users.User;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "product_name", nullable = false,  length = 100)
    private String productName;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    @Column(name = "image", nullable = true,  length = 900)
    private String image;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "condition", nullable = false) // indica si es nuevo o usado
    private boolean condition;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20) // indica pendiente de aprobación
    private ProductStatus status;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category idCategory;
    @ManyToOne
    @JoinColumn(name = "user_dpi", nullable = false)
    private User userDpi;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    @OneToMany(mappedBy = "productId")
    private List<CartProduct> cartProducts;
    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;
}