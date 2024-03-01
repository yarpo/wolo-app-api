package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "address_to_event")
public class AddressToEvent {

    public AddressToEvent(Event event, Address address) {
        this.event = event;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    @JsonBackReference
    private Address address;

    @OneToMany(mappedBy = "addressToEvent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shift> shifts = new ArrayList<>();
}
