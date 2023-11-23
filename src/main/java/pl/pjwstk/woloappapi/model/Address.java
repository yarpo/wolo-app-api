package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "description")
    private String addressDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Organisation> organisations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AddressToEvent> addressToEvents = new ArrayList<>();

}


