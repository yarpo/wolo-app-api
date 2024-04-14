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
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_num")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @OneToOne(mappedBy = "organisation")
    private User moderator;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "organisation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> events;
}
