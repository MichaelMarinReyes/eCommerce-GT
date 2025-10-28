package backend.models.management;

import backend.models.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sanctions")
public class Sanction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sanction", nullable = false)
    private Long idSanction;
    @ManyToOne
    @JoinColumn(name = "user_dpi", nullable = false)
    private User userDpi;
    @ManyToOne
    @JoinColumn(name = "moderator_dpi", nullable = false)
    private User moderatorDpi;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "status", nullable = false)
    private boolean status; // si está activa la sanción o ya concluyó
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "violation_type", nullable = false)
    private String violationType;
}
