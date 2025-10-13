package backend.models.market;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product", nullable = false)
    private Long idOrderProduct;
    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order idOrder;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "price", nullable = false)
    private double price;
}
