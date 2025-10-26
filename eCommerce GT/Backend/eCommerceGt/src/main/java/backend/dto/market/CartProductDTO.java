package backend.dto.market;

import backend.models.market.Category;
import backend.models.market.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductDTO {
    private Long id;
    private Long idCart;
    private ProductCartDTO product;
    private int quantity;
    private double price;
}