package backend.dto.market;

import backend.models.market.CartProduct;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartResponseDTO {
    private Long idCart;
    private String userDpi;
    private List<CartProduct> products;
    private double total;
    private Date createdAt;
}
