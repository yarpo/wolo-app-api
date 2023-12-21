package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "credential")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user")
    private UserEntity user;


}
