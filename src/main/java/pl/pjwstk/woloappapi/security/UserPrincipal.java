package pl.pjwstk.woloappapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.pjwstk.woloappapi.model.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static List<GrantedAuthority> convertStringToAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (role) {
            case "USER" -> authorities.add(UserRole.USER);
            case "ORGANIZATION" -> authorities.add(UserRole.ORGANIZATION);
            case "ADMIN" -> authorities.add(UserRole.ADMIN);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }
        return authorities;
    }

    public static UserPrincipal create(UserEntity user) {

        List<GrantedAuthority> authorities = convertStringToAuthorities(user.getRole().getName());

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}