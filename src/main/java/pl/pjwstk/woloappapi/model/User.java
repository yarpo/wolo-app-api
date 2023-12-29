package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
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
    @Pattern(regexp = "[0-9]{9}", message = "Phone number should consist of 9 digits")
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

    @OneToMany(mappedBy = "moderator", cascade = CascadeType.ALL)
    private List<Organisation> organisations;

    @OneToMany(mappedBy = "user")
    private List<ShiftToUser> shifts = new ArrayList<>();

    @Column(name = "password_hash", nullable = false)
    private String password_hash;

    @Column(name = "salt", nullable = false)
    private String salt;
}
