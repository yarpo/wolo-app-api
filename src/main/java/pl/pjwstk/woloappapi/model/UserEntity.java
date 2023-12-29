package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonIgnore
    private Role role;

    @Column(name = "is_pesel_verified", nullable = false)
    private boolean isPeselVerified;

    @Column(name = "is_agreement_signed", nullable = false)
    private boolean isAgreementSigned;

    @Column(name = "is_adult", nullable = false)
    private boolean isAdult;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "organisation_id", referencedColumnName = "id")
    private Organisation organisation;

    @OneToMany(mappedBy = "user")
    private List<ShiftToUser> shifts;

    @Column(name = "password", nullable = false)
    private String password;
}
