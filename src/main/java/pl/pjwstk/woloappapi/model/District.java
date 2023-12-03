package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "District name is required")
    @Size(max = 100, message = "District name cannot exceed 100 characters")
    private String name;

    @Column(name = "city")
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
}

