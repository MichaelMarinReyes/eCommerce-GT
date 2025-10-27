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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "user_dpi")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_cart",  nullable = false)
    private ShoppingCart shoppingCart;
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    @Column(name = "status", nullable = false)
    private boolean delivered;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;
    @OneToMany(mappedBy = "idOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> products;
}