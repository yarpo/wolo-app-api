package pl.pjwstk.woloappapi.model;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "shift_to_user")
public class ShiftToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "id")
    private Shift shift;

    @Column(name = "is_on_reserve_list", nullable = false)
    private boolean isOnReserveList;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;


}
