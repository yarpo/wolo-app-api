package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "home_num", nullable = false)
    private String homeNum;

    @ManyToOne
    @JoinColumn(name = "district_id") // nullable = false
    @JsonBackReference
    private District district;

    @Column(name = "description")
    private String addressDescription;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Organisation> organisations = new ArrayList<>();

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AddressToEvent> addressToEvents = new ArrayList<>();
}
