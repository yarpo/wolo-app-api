package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "category_to_event")
public class CategoryToEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
