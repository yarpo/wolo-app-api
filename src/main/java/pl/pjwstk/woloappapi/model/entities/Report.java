package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "report_pl", nullable = false)
    private String reportPL;

    @Column(name = "report_en", nullable = false)
    private String reportEN;

    @Column(name = "report_ua", nullable = false)
    private String reportUA;

    @Column(name = "report_ru", nullable = false)
    private String reportRU;

    @Column(name = "published")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
    private Event event;
}
