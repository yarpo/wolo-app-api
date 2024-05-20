package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "name_pl", nullable = false)
    private String namePL;

    @Column(name = "name_en", nullable = false)
    private String nameEN;

    @Column(name = "name_ua", nullable = false)
    private String nameUA;

    @Column(name = "name_ru", nullable = false)
    private String nameRU;

    @Column(name = "description_pl", nullable = false)
    private String descriptionPL;

    @Column(name = "description_en", nullable = false)
    private String descriptionEN;

    @Column(name = "description_ua", nullable = false)
    private String descriptionUA;

    @Column(name = "description_ru", nullable = false)
    private String descriptionRU;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CategoryToEvent> categories;

    @Column(name = "is_pesel_ver_req", nullable = false)
    private boolean isPeselVerificationRequired;

    @Column(name = "is_agreement_needed", nullable = false)
    private boolean isAgreementNeeded;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Shift> shifts;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", namePL='" + namePL + '\'' +
                ", nameEN='" + nameEN + '\'' +
                ", nameUA='" + nameUA + '\'' +
                ", nameRU='" + nameRU + '\'' +
                ", descriptionPL='" + descriptionPL + '\'' +
                ", descriptionEN='" + descriptionEN + '\'' +
                ", descriptionUA='" + descriptionUA + '\'' +
                ", descriptionRU='" + descriptionRU + '\'' +
                ", date=" + date +
                ", isPeselVerificationRequired=" + isPeselVerificationRequired +
                ", isAgreementNeeded=" + isAgreementNeeded +
                ", imageUrl='" + imageUrl + '\'' +
                ", approved=" + approved +
                '}';
    }
}
