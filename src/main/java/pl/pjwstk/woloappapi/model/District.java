package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
}
