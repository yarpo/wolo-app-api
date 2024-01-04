package pl.pjwstk.woloappapi.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,
    ORGANIZATION,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
