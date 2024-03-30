package pl.pjwstk.woloappapi.model.entities;

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
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<CategoryToEvent> categories;

    @Column(name = "is_pesel_ver_req", nullable = false)
    private boolean isPeselVerificationRequired;

    @Column(name = "is_agreement_needed", nullable = false)
    private boolean isAgreementNeeded;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<AddressToEvent> addressToEvents;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

}
