package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_old")
    private boolean isOld;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityId=" + (city != null ? city.getId() : "null") +
                '}';
    }
}
