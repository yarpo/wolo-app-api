package pl.pjwstk.woloappapi.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenConfig {
    private final String tokenSecret;
    private final long tokenExpirationMsec;
}
