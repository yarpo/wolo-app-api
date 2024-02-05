package pl.pjwstk.woloappapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@PropertySource("file:${user.dir}/.env")
public class AppConfig {
    private final Environment env;

    @Bean
    public TokenConfig tokenConfig() {
        String tokenSecret = env.getProperty("TOKEN_SECRET");
        long tokenExpirationMsec = Long.parseLong(Objects.requireNonNullElse(env.getProperty("TOKEN_EXPIRATION_MSEC"), "0"));
        return new TokenConfig(tokenSecret, tokenExpirationMsec);
    }




}
