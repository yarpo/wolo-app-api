package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname cannot exceed 50 characters")
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp="[0-9]{9}", message="Phone number should consist of 9 digits")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column(name = "is_pesel_verified", nullable = false)
    private boolean isPeselVerified;

    @Column(name = "is_agreement_signed", nullable = false)
    private boolean isAgreementSigned;

    @Column(name = "is_adult", nullable = false)
    private boolean isAdult;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organisation organization;

    @OneToMany(mappedBy = "user")
    private List<ShiftToUser> shifts = new ArrayList<>();

}
