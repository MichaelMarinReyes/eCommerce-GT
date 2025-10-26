package backend.dto.market;

import backend.models.market.Product;
import backend.models.market.ShoppingCart;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
    private Long idCartProduct;
    private Long cartId;
    private String userName;
    private ProductDTO product;
    private int quantity;
    private double price;
}