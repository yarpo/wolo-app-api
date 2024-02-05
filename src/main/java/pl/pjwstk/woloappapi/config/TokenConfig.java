package pl.pjwstk.woloappapi.config;

import lombok.Getter;

@Getter
public record TokenConfig(String tokenSecret, long tokenExpirationMsec) {
}
