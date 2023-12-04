package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryToEvent> categoryToEventList;
}
