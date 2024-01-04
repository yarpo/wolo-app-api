package pl.pjwstk.woloappapi.security;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AuthResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
