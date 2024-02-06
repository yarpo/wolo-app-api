package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "description_pl")
    private String addressDescriptionPL;

    @Column(name = "description_en")
    private String addressDescriptionEN;

    @Column(name = "description_ua")
    private String addressDescriptionUA;

    @Column(name = "description_ru")
    private String addressDescriptionRU;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Organisation> organisations = new ArrayList<>();

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AddressToEvent> addressToEvents = new ArrayList<>();
}
