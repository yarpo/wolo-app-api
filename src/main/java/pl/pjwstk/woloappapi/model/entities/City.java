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
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_old")
    private boolean isOld;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<District> districts;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<Event> events;
}
