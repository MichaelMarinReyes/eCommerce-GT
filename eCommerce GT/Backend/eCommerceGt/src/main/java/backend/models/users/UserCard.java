package backend.models.users;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_cards")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card", nullable = false)
    private Long idCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_dpi",  nullable = false)
    private User userDpi;
    @Column(name = "card_number",  nullable = false, length = 16)
    private String cardNumber;
    @Column(name = "card_holder", nullable = false)
    private String cardHolder;
    @Column(name = "expiration_date",  nullable = false)
    private String expirationDate;
    @Column(name = "card_type", nullable = false)
    private String cardType;
    @Column(name = "security_code", nullable = false)
    private String securityCode;
}
