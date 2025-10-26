package backend.dto.market;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long idCategory;
    private String categoryName;
    private String description;
}