package backend.dto.market;

import backend.models.market.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductUpdateDTO {
    private Long productId;
    private String productName;
    private String description;
    private Category category;
    private double price;
    private String image;
    private int stock;
    private boolean condition;
}