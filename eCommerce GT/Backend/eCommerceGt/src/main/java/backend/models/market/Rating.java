package backend.models.market;

import backend.models.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rating", nullable = false)
    private Long idRating;
    @ManyToOne
    @JoinColumn(name = "user_dpi", nullable = false)
    private User userDpi;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;
    @Column(name = "starts", nullable = false)
    private int starts;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}