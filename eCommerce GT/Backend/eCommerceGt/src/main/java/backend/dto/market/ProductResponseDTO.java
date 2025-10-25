package backend.dto.market;

import backend.models.market.Category;
import backend.models.market.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean condition;
    private ProductStatus status;
    private Category category;
    private String image;
    private double averageRating;
}