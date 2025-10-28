package backend.dto.management;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionDTO {
    private Long idSanction;
    private String userDpi;
    private String userName;
    private String moderatorDpi;
    private String moderatorName;
    private String reason;
    private boolean status;
    private Date startDate;
    private Date endDate;
    private String violationType;
}