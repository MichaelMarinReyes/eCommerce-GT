package backend.dto.rating;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private Long idRating;
    private String userName;
    private int stars;
    private String comment;
    private Date createdAt;
}