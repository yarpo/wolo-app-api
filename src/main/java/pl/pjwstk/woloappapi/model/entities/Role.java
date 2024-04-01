package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> userEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "privilege_to_role",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = this.privileges
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getName()));
        return authorities;
    }

    @Override
    public String toString() {
        String userEntitiesString = "{" +
                userEntities.stream()
                        .map(user -> String.valueOf(user.getId()))
                        .collect(Collectors.joining(", ")) +
                "}";

        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userEntities=" + userEntitiesString +
                ", privileges=" + privileges +
                '}';
    }
}
