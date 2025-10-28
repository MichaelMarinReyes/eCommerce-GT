package backend.dto.management;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SanctionCreateDTO {
    private String userDpi;
    private String moderatorDpi;
    private String violationType;
    private String reason;
    private Date endDate;
}
