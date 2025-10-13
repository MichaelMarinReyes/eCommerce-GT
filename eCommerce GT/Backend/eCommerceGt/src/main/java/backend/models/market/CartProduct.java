package backend.models.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_products")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart_product", nullable = false)
    private Long idCartProduct;
    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private ShoppingCart idCart;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;
    @Column(name = "quatity",  nullable = false)
    private int quatity;
    @Column(name = "price",  nullable = false)
    private double price;
}
