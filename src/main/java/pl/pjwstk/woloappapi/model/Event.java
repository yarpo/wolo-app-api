package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_pl")
    private String namePL;

    @Column(name = "name_en")
    private String nameEN;

    @Column(name = "name_ua")
    private String nameUA;

    @Column(name = "name_ru")
    private String nameRU;

    @Column(name = "description_pl")
    private String descriptionPL;

    @Column(name = "description_en")
    private String descriptionEN;

    @Column(name = "description_ua")
    private String descriptionUA;

    @Column(name = "description_ru")
    private String descriptionRU;

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
    private List<AddressToEvent> addressToEvents = new ArrayList<>();

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    @Column(name = "alt")
    private String alt;

}
