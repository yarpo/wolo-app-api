package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pjwstk.woloappapi.model.entities.Event;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report", nullable = false)
    private String report;

    @Column(name = "published")
    private boolean published;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
    private Event event;
}
