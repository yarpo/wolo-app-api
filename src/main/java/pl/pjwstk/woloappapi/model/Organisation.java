package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import jakarta.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone_num")
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 9, message = "Phone number must be 9 digits")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @Valid
    private Address address;

    @JsonIgnore
    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false)
    @Valid
    private User moderator;

    @Column(name = "logo_url")
    private String logoUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    private List<Event> events;


}
