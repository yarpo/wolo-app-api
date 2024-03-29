package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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

    public ShiftToUser(User user, Shift shift) {
        this.shift =shift;
        this.user = user;
    }
}
