package backend.dto.market;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductDTO {
    private Long productId;
    private String name;
    private String image;
    private double price;
    private int quantity;
    private String categoryName;
}