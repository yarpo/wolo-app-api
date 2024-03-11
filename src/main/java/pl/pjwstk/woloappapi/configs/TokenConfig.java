package pl.pjwstk.woloappapi.configs;

public record TokenConfig(String tokenSecret, long tokenExpirationMsec) {
}
