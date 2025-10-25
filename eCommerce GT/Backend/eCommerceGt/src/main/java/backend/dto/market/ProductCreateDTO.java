package backend.dto.market;

import backend.models.market.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    private Long productId;
    private String productName;
    private String description;
    private Category category;
    private double price;
    private String image;
    private int stock;
    private boolean condition;
}