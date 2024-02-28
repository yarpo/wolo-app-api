package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_to_event_id") // nullable = false
    private AddressToEvent addressToEvent;

    @OneToMany(mappedBy = "shift")
    private List<ShiftToUser> shiftToUsers = new ArrayList<>();

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "is_leader_required", nullable = false)
    private boolean isLeaderRequired;

    @Column(name = "required_min_age", nullable = false)
    private int requiredMinAge;

    @Column(name = "registered")
    private int registeredUsers;

    @Column(name = "directions")
    private String shiftDirections;
}
