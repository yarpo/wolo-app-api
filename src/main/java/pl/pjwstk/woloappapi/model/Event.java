package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_pl", nullable = false)
    private String namePL;

    @Column(name = "name_en")
    private String nameEN;

    @Column(name = "name_ua")
    private String nameUA;

    @Column(name = "name_ru")
    private String nameRU;

    @Column(name = "description_pl", nullable = false)
    private String descriptionPL;

    @Column(name = "description_en")
    private String descriptionEN;

    @Column(name = "description_ua")
    private String descriptionUA;

    @Column(name = "description_ru")
    private String descriptionRU;

    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    @JsonBackReference
    @JoinColumn(name = "organisation_en", nullable = false)
    private Organisation organisation;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CategoryToEvent> categories;

    @Column(name = "is_pesel_ver_req", nullable = false)
    private boolean isPeselVerificationRequired;

    @Column(name = "is_agreement_needed", nullable = false)
    private boolean isAgreementNeeded;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AddressToEvent> addressToEvents = new ArrayList<>();

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    public List<Category> getEventCategories() {
        List<Category> eventCategories = new ArrayList<>();
        for (CategoryToEvent categoryToEvent : categories) {
            eventCategories.add(categoryToEvent.getCategory());
        }
        return eventCategories;
    }
}
