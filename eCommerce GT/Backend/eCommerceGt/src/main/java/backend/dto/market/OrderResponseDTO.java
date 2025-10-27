package backend.dto.market;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private double totalAmount;
    private String delivered;
    private String userDpi;
    private Date createdAt;
    private Date deliveryDate;
    private List<OrderProductDTO> products;
}