package backend.dto.market;

import backend.models.market.Category;
import backend.models.market.ProductStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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