package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
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
    private District district;

    @Column(name = "description")
    private String addressDescription;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Organisation> organisations;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AddressToEvent> addressToEvents;
}
