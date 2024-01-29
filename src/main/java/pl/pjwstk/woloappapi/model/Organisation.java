package pl.pjwstk.woloappapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_num")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @JsonBackReference
    private Address address;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false)
    @JsonBackReference
    private UserEntity moderator;


    @Column(name = "logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Event> events;
}
