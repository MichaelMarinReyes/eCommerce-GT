package backend.dto.market;

import backend.dto.rating.RatingDTO;
import backend.models.market.Category;
import backend.models.market.ProductStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private List<RatingDTO>  ratings;
    private String sellerName;
    private Date createdAt;
    private Date updatedAt;
}