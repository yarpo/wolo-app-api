package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "address_to_event")
public class AddressToEvent {

    public AddressToEvent() {
    }

    public AddressToEvent(Event event, Address address) {
        this.event = event;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;


    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "addressToEvent", cascade = CascadeType.ALL)
    private List<Shift> shifts = new ArrayList<>();


}
