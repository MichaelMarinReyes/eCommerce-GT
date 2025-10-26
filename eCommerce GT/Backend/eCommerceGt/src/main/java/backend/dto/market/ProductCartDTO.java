package backend.dto.market;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCartDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private double price;
    private int stock;
    private boolean condition;
    private String status;
    private CategoryDTO category;
    private String userName;
}