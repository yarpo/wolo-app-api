package pl.pjwstk.woloappapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

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

    @ManyToMany(mappedBy = "roles")
    private List<User> userEntities;

    @ManyToMany
    @JoinTable(
            name = "privilege_to_role",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new java.util.ArrayList<>(getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getName()));
        return authorities;
    }
}
