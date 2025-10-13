package backend.models.market;

import backend.models.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private User userDpi;
    @ManyToOne
    @JoinColumn(name = "id_cart",  nullable = false)
    private ShoppingCart idCart;
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    @Column(name = "status", nullable = false)
    private boolean status;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;
}